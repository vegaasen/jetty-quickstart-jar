package com.vegaasen.http.jetty.run;

import com.vegaasen.http.jetty.container.ContainerProperties;
import com.vegaasen.http.jetty.container.variant.JettyContainer;
import com.vegaasen.http.jetty.model.Authentication;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.model.User;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class RunnableJetty {

    public static void main(String... args) {
        JettyContainer container = new JettyContainer();
        JettyArguments arguments = new JettyArguments();
        arguments.setRootPath("/");
        arguments.setContextPath("/");

        Authentication authentication = new Authentication();
        authentication.setRealm("Jetty Test Realm");
        authentication.setUserRoles(ContainerProperties.JETTY_USER_ROLES);
        authentication.setAllowedUsers(new User[]{new User.Builder().password("vegard").username("vegard").build()});
        authentication.setProtectedPath("/protected-area/");
        arguments.setAuthentication(authentication);

        container.startServer(arguments);
    }

}
