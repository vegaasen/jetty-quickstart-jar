package com.vegaasen.http.jetty.container.variant;

import com.vegaasen.http.jetty.model.JettyArguments;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public class JettyContainerTest {

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

    @After
    public void tearDown() throws Exception {

    }

}
