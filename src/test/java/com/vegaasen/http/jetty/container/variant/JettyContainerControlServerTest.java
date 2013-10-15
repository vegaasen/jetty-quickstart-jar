package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.container.variant.abs.JettyContainerAbstractTest;
import com.vegaasen.http.jetty.model.JettyArguments;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class JettyContainerControlServerTest extends JettyContainerAbstractTest {

    private JettyArguments arguments;

    @Before
    public void setUp() throws Exception {
        arguments = new JettyArguments();
        jettyContainer = new JettyContainer();
    }

    @Test
    public void shouldStartAndVerifyRunningServer() {
        jettyContainer.startServer(arguments);
        assertIsRunning();
        assertControlServerIsRunning();
    }

}
