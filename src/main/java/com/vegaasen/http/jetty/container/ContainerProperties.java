package com.vegaasen.http.jetty.container;

import com.vegaasen.http.jetty.model.User;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class ContainerProperties {

    public static final String[] JETTY_USER_ROLES = new String[]{"user"};
    public static final String[] JETTY_PROTOCOLS = new String[]{"http/1.1"};
    public static final String DEFAULT_PATH = "/";
    public static final String DEFAULT_CONTEXT_PATH =  DEFAULT_PATH + "jetty";
    public static final String DEFAULT_WEBAPP_RESOURCE = "/webapp/";
    public static final char SERVLET_MATCHER = '*';
    public static final int DEFAULT_PORT = 7000;
    public static final int DEFAULT_CONTROL_PORT = DEFAULT_PORT + 1;
    public static final int DEFAULT_HTTPS_PORT = DEFAULT_PORT + 443;
    public static final User DEFAULT_USER = new User.Builder().username("user").password("password").build();
    public static final String DEFAULT_REALM = "Jetty Application Server";

}
