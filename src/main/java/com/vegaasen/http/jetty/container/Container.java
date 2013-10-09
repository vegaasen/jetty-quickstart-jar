package com.vegaasen.http.jetty.container;

import org.eclipse.jetty.server.Server;

import javax.servlet.http.HttpServlet;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
interface Container {

    public Server getServer();

    public void startServer();

    public void startServer(String[] args);

    public void stopServer();

    public void stopServer(HttpServlet servlet);

    public boolean isRunning();


}
