package com.vegaasen.http.jetty.model;

import static com.vegaasen.http.jetty.container.ContainerProperties.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class HttpsConfiguration {

    private int httpsPort = DEFAULT_HTTPS_PORT;

    public int getHttpsPort() {
        return httpsPort;
    }

    public void setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
    }

}
