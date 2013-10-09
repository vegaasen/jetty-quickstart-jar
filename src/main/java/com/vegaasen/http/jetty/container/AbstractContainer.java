package com.vegaasen.http.jetty.container;

import org.eclipse.jetty.server.Server;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public abstract class AbstractContainer implements Container {

    //can be overridden
    public AbstractContainer() {
    }

    @Override
    public Server getServer() {
        throw new IllegalStateException("Not implemented");
    }

    //can be overridden
    public void start(Thread t) {
    }

    @Override
    public boolean isRunning() {
        throw new IllegalStateException("Not implemented");
    }
}
