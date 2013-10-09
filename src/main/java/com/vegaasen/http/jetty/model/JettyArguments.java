package com.vegaasen.http.jetty.model;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class JettyArguments {

    public static final String[] JETTY_USER_ROLES = new String[]{"user"};
    public static final String[] JETTY_PROTOCOLS = new String[]{"http/1.1"};
    public static final String DEFAULT_PATH = "/";
    public static final int DEFAULT_PORT = 7000;
    public static final int DEFAULT_CONTROL_PORT = DEFAULT_PORT + 1;
    public static final int DEFAULT_HTTPS_PORT = DEFAULT_PORT + 443;
    public static final User DEFAULT_USER = new User.Builder().username("user").password("password").build();

    private String[] protocols = JETTY_PROTOCOLS;
    private String root = DEFAULT_PATH;
    private String controlRoot = DEFAULT_PATH;
    private User[] allowedUsers = new User[]{DEFAULT_USER};
    private String[] userRoles = JETTY_USER_ROLES;
    private String protectedPath = DEFAULT_PATH + "*";
    private String unprotectedPaths;
    private String realm = "Jetty Application Server";

    public JettyArguments() {
    }

    public String[] getProtocols() {
        return protocols;
    }

    public void setProtocols(String[] protocols) {
        this.protocols = protocols;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getControlRoot() {
        return controlRoot;
    }

    public void setControlRoot(String controlRoot) {
        this.controlRoot = controlRoot;
    }

    public User[] getAllowedUsers() {
        return allowedUsers;
    }

    public void setAllowedUsers(User[] allowedUsers) {
        this.allowedUsers = allowedUsers;
    }

    public String[] getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(String[] userRoles) {
        this.userRoles = userRoles;
    }

    public String getProtectedPath() {
        return protectedPath;
    }

    public void setProtectedPath(String protectedPath) {
        this.protectedPath = protectedPath;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }
}
