package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.container.ContainerProperties;
import com.vegaasen.http.jetty.container.variant.abs.JettyContainerAbstractTest;
import com.vegaasen.http.jetty.jersey.rest.controller.TestController;
import com.vegaasen.http.jetty.storage.SimpleStorage;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.storage.model.Thing;
import com.vegaasen.http.jetty.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class JettyContainerWithJerseyTest extends JettyContainerAbstractTest {

    private JettyArguments arguments;

    @Before
    public void setUp() throws Exception {
        arguments = new JettyArguments();
        arguments.setWebAppResourceFolder("jerseywebapp");
        arguments.setContextPath("/");
        arguments.setRootPath("/");
        jettyContainer = new JettyContainer();
        SimpleStorage.INSTANCE.clearAllThings();
    }

    @Test
    public void shouldStartSimpleContextWithJersey() {
        jettyContainer.startServer(arguments);
        assertIsRunning();
    }

    @Test
    public void shouldGetSomeSimpleJerseyContent() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(ContainerProperties.DEFAULT_PORT, "/test/simple")
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains(TestController.RESPONSE_SIMPLE_JERSEY));
    }

    @Test
    public void shouldGetSimpleJerseyContent() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(
                        ContainerProperties.DEFAULT_PORT,
                        "/test/simpleWithParams/123456?ping=pong")
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("pong"));
        assertTrue(result.contains("123456"));
    }

    @Test
    public void shouldPerformSimplePostAndGetStuffFromTheSimpleStorage() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        final String thing = "stufftostoresomething";
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(
                        ContainerProperties.DEFAULT_PORT,
                        String.format("/test/simplePostWithParams?thing=%s", thing),
                        false,
                        true)
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        Thing stuffJerseySays = SimpleStorage.INSTANCE.getThingByTimestamp(Long.parseLong(result));
        assertNotNull(stuffJerseySays);
        assertNotNull(stuffJerseySays.getId());
        assertFalse(stuffJerseySays.getId().isEmpty());
        assertNotNull(stuffJerseySays.getName());
        assertFalse(stuffJerseySays.getName().isEmpty());
        assertEquals(thing, stuffJerseySays.getName());
    }

    @Test
    public void shouldGenerateSomeJsonByJersey() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(ContainerProperties.DEFAULT_PORT, "/test/simpleThatProducesJson")
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("things"));
        assertTrue(result.contains("id"));
        assertTrue(result.contains("name"));
    }

    @Test
    public void shouldGenerateSomeXmlByJersey() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(ContainerProperties.DEFAULT_PORT, "/test/simpleThatProducesXml")
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("xml"));
        assertTrue(result.contains("things"));
        assertTrue(result.contains("id"));
        assertTrue(result.contains("name"));
    }

}
