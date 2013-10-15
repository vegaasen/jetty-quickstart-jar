package com.vegaasen.http.jetty.model;

import com.vegaasen.http.jetty.container.ContainerDefaults;
import com.vegaasen.http.jetty.container.servlet.IControlServlet;

import javax.servlet.http.HttpServlet;

import static com.vegaasen.http.jetty.container.ContainerDefaults.DEFAULT_CONTROL_PORT;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class ControlServlet {

    private int httpControlPort = DEFAULT_CONTROL_PORT;
    private String controlPath = ContainerDefaults.DEFAULT_CONTROL_PATH;
    private Class<HttpServlet> controlClazz = null;

    public int getHttpControlPort() {
        return httpControlPort;
    }

    public void setHttpControlPort(int httpControlPort) {
        this.httpControlPort = httpControlPort;
    }

    public String getControlPath() {
        return controlPath;
    }

    public void setControlPath(String controlPath) {
        this.controlPath = controlPath;
    }

    public Class<HttpServlet> getControlClazz() {
        return controlClazz;
    }

    public void setControlClazz(Class<HttpServlet> controlClazz) {
        this.controlClazz = controlClazz;
    }
}
