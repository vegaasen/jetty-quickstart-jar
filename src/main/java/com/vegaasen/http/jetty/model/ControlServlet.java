package com.vegaasen.http.jetty.model;

import com.vegaasen.http.jetty.container.servlet.IControlServlet;

import static com.vegaasen.http.jetty.container.ContainerProperties.DEFAULT_CONTROL_PORT;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class ControlServlet {

    private int httpControlPort = DEFAULT_CONTROL_PORT;
    private IControlServlet controlServlet;

    public int getHttpControlPort() {
        return httpControlPort;
    }

    public void setHttpControlPort(int httpControlPort) {
        this.httpControlPort = httpControlPort;
    }

    public IControlServlet getControlServlet() {
        return controlServlet;
    }

    public void setControlServlet(IControlServlet controlServlet) {
        this.controlServlet = controlServlet;
    }
}
