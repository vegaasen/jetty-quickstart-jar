Jetty Quickstart Jar
====================

# Information

Simple jar-file that contains a couple of classes regarding Jetty.

# Requirements

This jar requires that the following jetty-jars is located on your classpath:

* org.eclipse.jetty
* * jetty-server
* * jetty-webapp
* * jetty-annotations (not required, but recommanded)

# Current Jetty Version Tested with

9.0.6.v20130930

# Usage

Add the jar-file to your classpath and configure the JettyContainer like the examples below.

# Small Jersey example with authentication

    JettyContainer container = new JettyContainer();
    JettyArguments arguments = new JettyArguments();

    Authentication authentication = new Authentication();
    authentication.setRealm("Jetty Test Realm");
    authentication.setUserRoles(ContainerDefaults.JETTY_USER_ROLES);
    authentication.setAllowedUsers(new User[]{new User.Builder().password("vegard").username("vegard").build()});
    authentication.setProtectedPath("/protected-area/");

    arguments.setAuthentication(authentication);
    arguments.setRootPath("/");
    arguments.setContextPath("/");

    container.startServer(arguments);

# Small Spring example

    JettyContainer container = new JettyContainer();
    JettyArguments arguments = new JettyArguments();

    arguments.addInitProperty("contextConfigLocation", "classpath:applicationContext.xml");

    arguments.setWebAppResourceFolder("springwebapp");
    arguments.setContextPath("/");
    arguments.setRootPath("/");

    arguments.addRequestListener(new RequestContextListener());
    arguments.addRequestListener(new ContextLoaderListener());

    final ServletHolder holder = new ServletHolder(new DispatcherServlet());
    holder.setInitParameter("contextConfigLocation", "classpath:/applicationContext.xml");
    holder.setInitOrder(1);

    arguments.addServlet(holder);

    container.startServer(arguments);

# Small Wicket example

    JettyContainer container = new JettyContainer();
    JettyArguments arguments = new JettyArguments();

    arguments.setWebAppResourceFolder("wicketwebapp");
    arguments.setContextPath("/");
    arguments.setRootPath("/");

    arguments.addInitProperty("configuration", "deployment");

    container.startServer(arguments);

# The examples doesn't work!

Allright, I've created a few examples as unittests, so please check them out in order to get a brief overview on how to use the wrapped container.

# Comments are welcome

Please leave comments if you see some potential for improvement!

# Problems

## Issues

* ControlServer not working correctly

## Improvements

* Alot! Models etc will change in the following days in order to make things easier. More testcases will also be added.
* Simpler interfaces
* Less methods in the JettyContainer / Container interface.

# Acknowledgements

Vegard Aasen