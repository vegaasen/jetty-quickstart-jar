package com.vegaasen.http.jetty.model;

import java.util.Arrays;

import static com.vegaasen.http.jetty.container.ContainerProperties.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class Authentication {

    private String realm = DEFAULT_REALM;
    private String unprotectedPaths;
    private String protectedPath = DEFAULT_PATH + "*";
    private User[] allowedUsers = new User[]{DEFAULT_USER};
    private String[] userRoles = JETTY_USER_ROLES;

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getUnprotectedPaths() {
        return unprotectedPaths;
    }

    public void setUnprotectedPaths(String unprotectedPaths) {
        this.unprotectedPaths = unprotectedPaths;
    }

    public String getProtectedPath() {
        return protectedPath;
    }

    public void setProtectedPath(String protectedPath) {
        if (!protectedPath.endsWith("*")) {
            if (!protectedPath.endsWith("/")) {
                protectedPath = protectedPath + '/';
            }
            protectedPath = protectedPath + '*';
        }
        if (!protectedPath.startsWith("/")) {
            protectedPath = '/' + protectedPath;
        }
        this.protectedPath = protectedPath;
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

    @Override
    public String toString() {
        return "Authentication{" +
                "realm='" + realm + '\'' +
                ", unprotectedPaths='" + unprotectedPaths + '\'' +
                ", protectedPath='" + protectedPath + '\'' +
                ", allowedUsers=" + Arrays.toString(allowedUsers) +
                ", userRoles=" + Arrays.toString(userRoles) +
                '}';
    }
}
