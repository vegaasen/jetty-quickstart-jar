package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.container.ContainerProperties;
import com.vegaasen.http.jetty.model.Authentication;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.model.User;
import com.vegaasen.http.jetty.utils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class JettyContainerTest {

    private static final String TEXT_FROM_HTML = "Ello!:)";
    private static final String RESOURCE_WITHOUT_CONTEXT_PATH = "/html/resource.html";
    private static final String RESOURCE_WITH_DEFAULT_CONTEXT_PATH = "/jetty" + RESOURCE_WITHOUT_CONTEXT_PATH;

    private JettyArguments arguments;
    private JettyContainer jettyContainer;

    @Before
    public void setUp() throws Exception {
        arguments = new JettyArguments();
        jettyContainer = new JettyContainer();
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailBecauseOfNoArgumentsProvided() {
        jettyContainer.startServer(null);
    }

    @Test
    public void shouldStartAndRun() throws InterruptedException {
        jettyContainer.startServer();
        boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotStartWhenResourceDoesNotExists() {
        arguments.setWebAppResourceFolder("nosuchwebappbase");
        jettyContainer.startServer(arguments);
    }

    @Test
    public void shouldStartEvenResourceFolderNotExists() {
        arguments.setWebAppResourceFolder("nosuchwebappbase");
        jettyContainer.setSkipResourcePathVerification(true);
        jettyContainer.startServer();
        boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
    }

    @Test
    public void shouldStopWebServer() {
        jettyContainer.startServer();
        boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
        jettyContainer.stopServer();
        running = jettyContainer.isRunning();
        assertNotNull(running);
        assertFalse(running);
    }

    @Test
    public void shouldStartOnSpecificPort() throws IOException {
        arguments.setHttpPort(9090);
        arguments.setWebAppResourceFolder("htmlwebapp");
        arguments.setContextPath("html");
        jettyContainer.startServer(arguments);
        boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
        try {
            assertTrue(TestUtils.available(9090));
            fail("Should be in use.");
        } catch (IOException e) {
            assertTrue(true);
        }
    }

    @Test
    public void shouldStartOnSpecificPortAndFetchSomeContent() throws IOException {
        arguments.setHttpPort(9090);
        arguments.setWebAppResourceFolder("htmlwebapp");
        jettyContainer.startServer(arguments);
        boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
        String stream = TestUtils.stringFromInputStream(TestUtils.streamFromHttpPort(9090, RESOURCE_WITH_DEFAULT_CONTEXT_PATH));
        assertNotNull(stream);
        assertFalse(stream.isEmpty());
        assertTrue(stream.contains(TEXT_FROM_HTML));
    }

    @Test
    public void shouldGetContentByDifferentPathThanDefault() throws IOException {
        arguments.setWebAppResourceFolder("htmlwebapp");
        jettyContainer.startServer(arguments);
        boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
        String stream = TestUtils.stringFromInputStream(TestUtils.streamFromHttpPort(
                ContainerProperties.DEFAULT_PORT,
                RESOURCE_WITH_DEFAULT_CONTEXT_PATH));
        assertNotNull(stream);
        assertFalse(stream.isEmpty());
        assertTrue(stream.contains(TEXT_FROM_HTML));
        jettyContainer.stopServer();
        running = jettyContainer.isRunning();
        assertNotNull(running);
        assertFalse(running);
        arguments.setContextPath("/");
        jettyContainer.startServer(arguments);
        running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
        stream = TestUtils.stringFromInputStream(TestUtils.streamFromHttpPort(
                ContainerProperties.DEFAULT_PORT,
                RESOURCE_WITHOUT_CONTEXT_PATH));
        assertNotNull(stream);
        assertFalse(stream.isEmpty());
        assertTrue(stream.contains(TEXT_FROM_HTML));
    }

    @Test
    public void shouldPrintErrorStreamOnNoDocumentFound() throws IOException {
        arguments.setWebAppResourceFolder("htmlwebapp");
        jettyContainer.startServer(arguments);
        boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
        String stream = TestUtils.stringFromInputStream(TestUtils.streamFromHttpPort(
                ContainerProperties.DEFAULT_PORT,
                "/non/existing/path/index.html", true));
        assertNotNull(stream);
        assertTrue(stream.isEmpty());
    }

    @Test
    public void shouldConfigureProtectedArea() throws IOException {
        arguments.setContextPath("/");
        Authentication authentication = new Authentication();
        authentication.setRealm("Jetty Test Realm");
        authentication.setUserRoles(ContainerProperties.JETTY_USER_ROLES);
        authentication.setAllowedUsers(new User[]{new User.Builder().password("vegard").username("vegard").build()});
        authentication.setProtectedPath("/protected-area/");
        arguments.setAuthentication(authentication);
        jettyContainer.startServer(arguments);
        boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
        String stream = TestUtils.stringFromInputStream(TestUtils.streamFromHttpPort(
                ContainerProperties.DEFAULT_PORT,
                "/jetty.html"));
        assertNotNull(stream);
        assertFalse(stream.isEmpty());
        stream = TestUtils.stringFromInputStream(TestUtils.streamFromHttpPort(
                ContainerProperties.DEFAULT_PORT,
                "/protected-area/protected-resource.html"));
        assertNotNull(stream);
        assertTrue(stream.isEmpty());
    }

    @After
    public void tearDown() throws Exception {
        jettyContainer.stopServer();
    }

}
