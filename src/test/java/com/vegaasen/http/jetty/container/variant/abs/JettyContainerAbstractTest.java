package com.vegaasen.http.jetty.container.variant.abs;

import com.vegaasen.http.jetty.container.variant.JettyContainer;
import org.junit.After;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public abstract class JettyContainerAbstractTest {

    protected JettyContainer jettyContainer;

    @After
    public void tearDown() throws Exception {
        jettyContainer.stopServer();
        System.out.println("Server stopped.");
    }

    public void assertIsRunning() {
        final boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
    }

}
