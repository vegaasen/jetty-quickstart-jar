package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.container.variant.abs.JettyContainerAbstractTest;
import com.vegaasen.http.jetty.model.JettyArguments;
import com.vegaasen.http.jetty.utils.TestUtils;
import com.vegaasen.http.jetty.wicket.page.TestPage;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class JettyContainerWithWicketTest extends JettyContainerAbstractTest {

    private static final int WICKET_HTTP_PORT = 7070;

    private JettyArguments arguments;

    @Before
    public void setUp() {
        arguments = new JettyArguments();
        arguments.setWebAppResourceFolder("wicketwebapp");
        arguments.setContextPath("/");
        arguments.setRootPath("/");
        arguments.addInitProperty("configuration", "deployment");
        arguments.setHttpPort(WICKET_HTTP_PORT);
        arguments.setControlServlet(null);
        jettyContainer = new JettyContainer();
    }

    @Test
    public void shouldStartWithWicketApplication() {
        jettyContainer.startServer(arguments);
        assertIsRunning();
    }

    @Test
    public void shouldStartAndRequestBaseOfTest() throws IOException {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        String result = TestUtils.stringFromInputStream(
                TestUtils.streamFromHttpPort(
                        WICKET_HTTP_PORT,
                        "/")
        );
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains(TestPage.DEFAULT_OUTPUT_HTML));
    }

}
