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

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public abstract class AbstractContainer implements Container {

    protected static Server webServer;
    protected static Server controlServer;

    //can be overridden
    public AbstractContainer() {
    }

    @Override
    public Server getServer() {
        throw new IllegalStateException("Not implemented");
    }

    //can be overridden
    public void start(Thread t) {
    }

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

}
