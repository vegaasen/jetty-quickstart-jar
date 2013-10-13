package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.container.AbstractContainer;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.utils.Validator;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.http.HttpServlet;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class JettyContainer extends AbstractContainer implements Serializable {

    private static final Logger LOG = Logger.getLogger(JettyContainer.class.getName());
    private static final long serialVersionUID = 42L;
    private boolean skipResourcePathVerification = false;

    @Override
    public void start() {
        startServer();
    }

    @Override
    public void startServer() {
        startServer(new JettyArguments());
    }

    @Override
    public void startServer(final JettyArguments args) {
        Validator.assertValidBasicArguments(args);
        if (!skipResourcePathVerification) {
            Validator.assertResourceBaseFolderExists(args);
        }
        try {
            webServer = new Server();
            webServer.setConnectors(assembleConnectors(args));
            final WebAppContext webAppContext = new WebAppContext();
            webAppContext.setClassLoader(new WebAppClassLoader(webAppContext));
            webAppContext.setContextPath(args.getContextPath());
            webAppContext.setBaseResource(Resource.newClassPathResource(args.getWebAppResourceFolder(), true, true));
            if (args.hasProperties()) {
                for (Map.Entry<String, String> property : args.getInitProperties().entrySet()) {
                    if (property != null) {
                        webAppContext.setInitParameter(property.getKey(), property.getValue());
                    }
                }
            }
            ConstraintSecurityHandler basicAuth = conditionallyAddBasicAuthentication(args);
            args.addHandler(basicAuth);
            args.addHandler(webAppContext);
            webServer.setHandler(assembleHandlers(args.getHandlers().toArray(new Handler[args.getHandlers().size()])));
            webServer.start();
            LOG.info(String.format("Jetty now started and available on: {%s}", args.printRequestableUrls()));
        } catch (Exception e) {
            LOG.severe("Unable to start webserver. Reason: " + e.getMessage());
            try {
                webServer.stop();
            } catch (Exception ex) {
                LOG.warning("Unable to stop. Most likely the webServer is not started, and it can therefore not be stopped.");
            }
            webServer = null;
            LOG.severe("The webServer was not started.");
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void stopServer() {
        if (webServer != null) {
            if (webServer.isStopped()) {
                LOG.info("WebServer has already been stopped.");
                return;
            }
            try {
                while (webServer.isRunning()) {
                    webServer.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOG.info("WebServer has been shut down.");
        }
    }

    @Override
    public void stopServer(final HttpServlet servlet) {

    }

    @Override
    public boolean isRunning() {
        return webServer != null && webServer.isStarted() && webServer.isRunning();
    }

    public void setSkipResourcePathVerification(final boolean skipResourcePathVerification) {
        this.skipResourcePathVerification = skipResourcePathVerification;
    }
}
