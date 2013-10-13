package com.vegaasen.http.jetty.run;

import com.vegaasen.http.jetty.container.ContainerProperties;
import com.vegaasen.http.jetty.container.variant.JettyContainer;
import com.vegaasen.http.jetty.model.Authentication;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.model.User;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class RunnableJetty {

    public static void main(String... args) {
        JettyContainer container = new JettyContainer();
        JettyArguments arguments = new JettyArguments();

        Authentication authentication = new Authentication();
        authentication.setRealm("Jetty Test Realm");
        authentication.setUserRoles(ContainerProperties.JETTY_USER_ROLES);
        authentication.setAllowedUsers(new User[]{new User.Builder().password("vegard").username("vegard").build()});
        authentication.setProtectedPath("/protected-area/");

        arguments.setAuthentication(authentication);
        arguments.setRootPath("/");
        arguments.setContextPath("/");

        container.startServer(arguments);
    }

}
