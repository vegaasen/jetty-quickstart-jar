package com.vegaasen.http.jetty.container.variant.abs;

import com.vegaasen.http.jetty.container.variant.JettyContainer;
import com.vegaasen.http.jetty.storage.SimpleStorage;
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
        jettyContainer.stopControlServer();
        System.out.println("Server stopped.");
        SimpleStorage.INSTANCE.clearAllThings();
    }

    protected void assertIsRunning() {
        final boolean running = jettyContainer.isRunning();
        assertNotNull(running);
        assertTrue(running);
    }

    protected void assertControlServerIsRunning() {
        final boolean running = jettyContainer.isControlServerRunning();
        assertNotNull(running);
        assertTrue(running);
    }

}
