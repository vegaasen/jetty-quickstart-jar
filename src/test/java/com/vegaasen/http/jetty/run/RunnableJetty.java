package com.vegaasen.http.jetty.run;

import com.vegaasen.http.jetty.container.ContainerProperties;
import com.vegaasen.http.jetty.container.variant.JettyContainer;
import com.vegaasen.http.jetty.model.Authentication;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.model.User;
import com.vegaasen.http.jetty.utils.TestUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletRequestListener;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class RunnableJetty {

    public static void main(String... args) {
        wicket();
    }

    private static void wicket() {
        JettyContainer container = new JettyContainer();
        JettyArguments arguments = new JettyArguments();

        arguments.setWebAppResourceFolder("wicketwebapp");
        arguments.setContextPath("/");
        arguments.setRootPath("/");

        arguments.addInitProperty("configuration", "deployment");

        container.startServer(arguments);
    }

    private static void spring() {
        JettyContainer container = new JettyContainer();
        JettyArguments arguments = new JettyArguments();

        arguments.addInitProperty("contextConfigLocation", "classpath:applicationContext.xml");

        arguments.setWebAppResourceFolder("springwebapp");
        arguments.setContextPath("/");
        arguments.setRootPath("/");

        arguments.addRequestListener(new RequestContextListener());
        arguments.addRequestListener(new ContextLoaderListener());

        arguments.addServlet(TestUtils.getSpringServletHolder());

        container.startServer(arguments);
    }

    private static void jersey() {
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
