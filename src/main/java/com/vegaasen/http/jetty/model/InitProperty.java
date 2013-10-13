package com.vegaasen.http.jetty.model;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class InitProperty {

    public static final String CHARSET = "org.eclipse.jetty.util.URI.charset";
    public static final String SESSION_COOKIE = "org.eclipse.jetty.servlet.SessionCookie"; // default JSESSIONID
    public static final String SESSION_COOKIE_DOMAIN = "org.eclipse.jetty.servlet.SessionDomain"; // default -
    public static final String SESSION_COOKIE_PATH = "org.eclipse.jetty.servlet.SessionPath"; // default -
    public static final String SESSION_PATH_PARAM_NAME = "org.eclipse.jetty.servlet.SessionIdPathParameterName"; // default jsessionid
    public static final String SESSION_COOKIE_MAX_AGE = "org.eclipse.jetty.servlet.MaxAge"; // default -1

    private InitProperty(){
    }

}
