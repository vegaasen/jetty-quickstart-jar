package com.vegaasen.http.jetty.utils;

import com.vegaasen.http.jetty.model.Authentication;
import com.vegaasen.http.jetty.model.JettyArguments;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class Validator {

    private Validator() {
    }

    public static void assertValidBasicArguments(final JettyArguments arguments) {
        if (arguments != null) {
            if (arguments.getContextPath() == null ||
                    arguments.getHttpPort() <= 0 ||
                    arguments.getRootPath() == null ||
                    arguments.getWebAppResourceFolder() == null) {
                throw new IllegalArgumentException(String.format("Illegal arguments provided. Object: {%s}", arguments.toString()));
            }
            return;
        }
        throw new IllegalArgumentException("JettyArguments cannot be null.");
    }

    public static void assertResourceBaseFolderExists(final JettyArguments arguments) {
        String resourcePath = arguments.getWebAppResourceFolder();
        resourcePath = (resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath);
        if (Validator.class.getResource(resourcePath) == null) {
            throw new IllegalArgumentException(
                    String.format("Expected resource-folder does not exists. Missing <%s> folder?", resourcePath)
            );
        }
    }

    public static void assertBasicAuthenticationArguments(final Authentication authentication) {
        if (authentication != null) {
            if (authentication.getProtectedPath() == null ||
                    authentication.getUserRoles() == null ||
                    authentication.getUserRoles().length == 0 ||
                    authentication.getAllowedUsers() == null ||
                    authentication.getAllowedUsers().length == 0) {
                throw new IllegalArgumentException(String.format(
                        "Illegal arguments provided. Object: {%s}",
                        authentication.toString()));
            }
        }
    }

}
