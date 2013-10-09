package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.container.AbstractContainer;
import com.vegaasen.http.jetty.model.JettyArguments;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.http.HttpServlet;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class JettyContainer extends AbstractContainer implements Serializable {

    private static final Logger LOG = Logger.getLogger(JettyContainer.class.getName());
    private static final long serialVersionUID = 42L;

    @Override
    public void startServer() {
        startServer(new JettyArguments());
    }

    @Override
    public void startServer(JettyArguments args) {
        try {
            webServer = new Server();
            webServer.setConnectors(assembleConnectors(args));
            final WebAppContext webAppContext = new WebAppContext();
            webAppContext.setClassLoader(new WebAppClassLoader(webAppContext));
            webAppContext.setContextPath(args.getContextPath());
            webAppContext.setBaseResource(Resource.newClassPathResource(args.getWebAppFolder(), true, true));
            webServer.setHandler(webAppContext);
            assembleBasicAuthentication(webAppContext, args);
            webServer.start();
        } catch (Exception e) {
            LOG.severe("Unable to start webserver. Reason: " + e.getMessage());
        } finally {
            try {
                webServer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            webServer = null;
        }
    }

    @Override
    public void stopServer() {

    }

    @Override
    public void stopServer(HttpServlet servlet) {

    }

}
