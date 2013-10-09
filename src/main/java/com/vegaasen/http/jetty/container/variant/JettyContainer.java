package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.container.AbstractContainer;
import org.eclipse.jetty.server.Server;

import javax.servlet.http.HttpServlet;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class JettyContainer extends AbstractContainer implements Serializable {

    private static final Logger LOG = Logger.getLogger(JettyContainer.class.getName());
    private static final long serialVersionUID = 42L;

    private static Server webServer;
    private static Server controlServer;

    @Override
    public void startServer() {

    }

    @Override
    public void startServer(String[] args) {

    }

    @Override
    public void stopServer() {

    }

    @Override
    public void stopServer(HttpServlet servlet) {

    }

}
