package com.vegaasen.http.jetty.container;

import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.model.User;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Credential;
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

    public void start(Thread t) {
    }

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
        if (args.getHttpsPort() > 0) {
            //todo
        }
        if (connectors.isEmpty()) {
            throw new RuntimeException("No controllers defined, even though they were expected to be.");
        }
        return connectors.toArray(new Connector[connectors.size()]);
    }

    protected void assembleBasicAuthentication(final WebAppContext context, final JettyArguments args) {
        if (context == null) {
            return;
        }
        if (args == null) {
            return;
        }
        final Constraint constraint = new Constraint();
        constraint.setName(Constraint.__BASIC_AUTH);
        constraint.setRoles(args.getUserRoles());
        constraint.setAuthenticate(true);

        final ConstraintMapping basicAuthMapping = new ConstraintMapping();
        basicAuthMapping.setConstraint(constraint);
        basicAuthMapping.setPathSpec(args.getProtectedPath());

        final HashLoginService loginService = new HashLoginService(args.getRealm());
        for (User user : args.getAllowedUsers()) {
            loginService.putUser(user.getUsername(), Credential.getCredential(user.getPassword()), args.getUserRoles());
        }
        final ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
        securityHandler.setLoginService(loginService);

        securityHandler.addConstraintMapping(basicAuthMapping);

        context.setHandler(securityHandler);
    }

    public final class ControlServlet extends HttpServlet implements Serializable {

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

        private void stopServer(final boolean justDie) {
            final long timeToServerDeath = (justDie) ? 0L : TimeUnit.SECONDS.toMillis(2);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    stop();
                    try {
                        Thread.sleep(timeToServerDeath);
                    } catch (Exception e) {
                        //eating the exception for now.
                    }
                    System.exit(0);
                }
            }, timeToServerDeath);
        }

    }

}
