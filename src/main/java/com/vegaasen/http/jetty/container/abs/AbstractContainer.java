package com.vegaasen.http.jetty.container.abs;

import com.vegaasen.http.jetty.container.Container;
import com.vegaasen.http.jetty.container.ContainerDefaults;
import com.vegaasen.http.jetty.container.servlet.IControlServlet;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.model.User;
import com.vegaasen.http.jetty.utils.Validator;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public abstract class AbstractContainer implements Container {

    public static final String COMMAND = "command";
    public static final String STOP = "stop", KILL = "kill", STATUS = "status";

    protected static Server webServer;
    protected static Server controlServer;

    public AbstractContainer() {
    }

    @Override
    public Server getServer() {
        throw new IllegalStateException("Not implemented");
    }

    public abstract void start();

    public abstract void stop();

    @Override
    public boolean isRunning() {
        throw new IllegalStateException("Not implemented");
    }

    protected Connector[] assembleConnectors(final JettyArguments args) {
        if (args == null) {
            throw new IllegalArgumentException("The arguments is null.");
        }
        final List<Connector> connectors = new ArrayList<Connector>();
        if (args.getHttpPort() > 0) {
            final ServerConnector httpConnector = new ServerConnector(webServer);
            httpConnector.setPort(args.getHttpPort());
            connectors.add(httpConnector);
        }
        if (args.getHttpsConfiguration() != null && args.getHttpsConfiguration().getHttpsPort() > 0) {
            //todo
        }
        if (connectors.isEmpty()) {
            throw new RuntimeException("No controllers defined, even though they were expected to be.");
        }
        return connectors.toArray(new Connector[connectors.size()]);
    }

    protected ConstraintSecurityHandler conditionallyAddBasicAuthentication(final JettyArguments args) {
        if (args == null) {
            return null;
        }
        if (args.getAuthentication() == null) {
            return null;
        }
        Validator.assertBasicAuthenticationArguments(args.getAuthentication());
        final HashLoginService loginService = new HashLoginService();
        loginService.setName(args.getAuthentication().getRealm());
        for (User user : args.getAuthentication().getAllowedUsers()) {
            loginService.putUser(
                    user.getUsername(),
                    Credential.getCredential(user.getPassword()),
                    args.getAuthentication().getUserRoles()
            );
        }

        final Constraint constraint = new Constraint();
        constraint.setName(Constraint.__BASIC_AUTH);
        constraint.setRoles(args.getAuthentication().getUserRoles());
        constraint.setAuthenticate(true);

        final ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setConstraint(constraint);
        constraintMapping.setPathSpec(args.getAuthentication().getProtectedPath());


        final ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.setRealmName(args.getAuthentication().getRealm());
        securityHandler.setAuthenticator(new BasicAuthenticator());
        securityHandler.addConstraintMapping(constraintMapping);
        securityHandler.setLoginService(loginService);

        return securityHandler;
    }

    public HandlerCollection assembleHandlers(final Handler... handlers) {
        if (handlers == null || handlers.length == 0) {
            throw new IllegalArgumentException("No handlers were found, even though there was expected to " +
                    "be at least one present.");
        }
        final HandlerCollection collection = new HandlerCollection();
        for (Handler h : handlers) {
            if (h != null) {
                collection.addHandler(h);
            }
        }
        return collection;
    }

    public void startControlServer(final JettyArguments arguments) throws Exception {
        if (arguments == null || arguments.getControlServlet() == null) {
            return;
        }
        controlServer = new Server();
        final ServerConnector httpControlConnector = new ServerConnector(controlServer);
        httpControlConnector.setPort(
                arguments.getControlServlet().getHttpControlPort() > 0 ?
                        arguments.getControlServlet().getHttpControlPort() :
                        ContainerDefaults.DEFAULT_CONTROL_PORT);
        controlServer.addConnector(httpControlConnector);
        final WebAppContext webAppContext = new WebAppContext();
        webAppContext.setClassLoader(new WebAppClassLoader(webAppContext));
        webAppContext.setContextPath(ContainerDefaults.DEFAULT_CONTROL_PATH);
        webAppContext.addServlet(
                arguments.getControlServlet().getControlClazz() == null ?
                        ControlServlet.class :
                        arguments.getControlServlet().getControlClazz(),
                arguments.getControlServlet().getControlPath() != null ||
                        !arguments.getControlServlet().getControlPath().isEmpty() ?
                        arguments.getControlServlet().getControlPath() :
                        ContainerDefaults.DEFAULT_PATH);
        controlServer.setHandler(webAppContext);
        controlServer.start();
    }

    public void stopControlServer() {
        if (controlServer != null) {
            try {
                while (controlServer.isRunning()) {
                    controlServer.stop();
                }
            } catch (Exception e) {
                controlServer.destroy();
            } finally {
                controlServer = null;
            }
        }
    }

    public boolean isControlServerRunning() {
        return controlServer != null && controlServer.isStarted() && controlServer.isRunning();
    }

    public final class ControlServlet extends HttpServlet implements IControlServlet, Serializable {

        private static final long serialVersionUID = 42L;

        public ControlServlet() {
        }

        @Override
        protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
                throws ServletException, IOException {
            PrintWriter out = resp.getWriter();
            resp.setContentType("text/html");
            try {
                final String command = req.getParameter(COMMAND);
                if (command == null || command.isEmpty()) {
                    out.print(String.format("Command only accepts {%s, %s, %s}", STOP, KILL, STATUS));
                    return;
                }
                if (command.equals(STOP)) {
                    out.print("Stopping servlet container in a few seconds.");
                    stopServer(false);
                } else if (command.equals(KILL)) {
                    out.print("Killing servlet container now.");
                    stopServer(true);
                } else if (command.equals(STATUS)) {
                    out.print("Server is running just fine.");
                } else {
                    out.print(String.format("Command detected, but only accepts the following: {%s, %s, %s}", STOP, KILL, STATUS));
                }
            } catch (Exception e) {
                out.print(String.format("Unable to complete request because of: {%s}", e.getMessage()));
            }
        }

        public void stopServer(final boolean justDie) {
            final long timeToServerDeath = (justDie) ? 0L : TimeUnit.SECONDS.toMillis(2);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    stop();
                    try {
                        Thread.sleep(timeToServerDeath);
                    } catch (final Exception e) {
                        //eating the exception for now.
                    }
                    System.exit(0);
                }
            }, timeToServerDeath);
        }

    }

}
