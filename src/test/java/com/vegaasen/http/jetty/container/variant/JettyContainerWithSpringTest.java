package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.container.variant.abs.JettyContainerAbstractTest;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.spring.rest.controller.TestController;
import com.vegaasen.http.jetty.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class JettyContainerWithSpringTest extends JettyContainerAbstractTest {

    private static final int SPRING_HTTP_PORT = 9090;

    private JettyArguments arguments;

    @Before
    public void setUp() throws Exception {
        arguments = new JettyArguments();
        jettyContainer = new JettyContainer();
        arguments.addInitProperty("contextConfigLocation", "classpath:applicationContext.xml");
        arguments.setWebAppResourceFolder("springwebapp");
        arguments.setContextPath("/");
        arguments.setRootPath("/");
        arguments.addRequestListener(new RequestContextListener());
        arguments.addRequestListener(new ContextLoaderListener());
        arguments.addServlet(TestUtils.getSpringServletHolder());
        arguments.setHttpPort(SPRING_HTTP_PORT);
        arguments.setControlServlet(null);
    }

    @Test
    public void shouldStartSpringWebContainer() {
        jettyContainer.startServer(arguments);
        assertIsRunning();
    }

    @Test
    public void shouldStartAndRequestBaseOfTest() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(
                        SPRING_HTTP_PORT,
                        "/test/")
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("poor"));
    }

    @Test
    public void shouldStartWithSomeSpringContextAndMakeGetRequest() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(
                        SPRING_HTTP_PORT,
                        "/test/simpleTest")
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains(TestController.DEFAULT_SIMPLE_RESPONSE));
    }

    @Test
    public void shouldStartWithSomeSpringContextAndMakePostRequest() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        final String thing = "stufftostoresomething";
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(
                        SPRING_HTTP_PORT,
                        String.format("/test/simpleTest?thing=%s", thing),
                        false,
                        true
                )
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        String lastAddedResult = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(
                        SPRING_HTTP_PORT,
                        "/test/lastAddedThing")
        );
        assertNotNull(lastAddedResult);
        assertFalse(lastAddedResult.isEmpty());
        assertEquals(result, lastAddedResult);
    }

}
