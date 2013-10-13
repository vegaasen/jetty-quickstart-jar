package com.vegaasen.http.jetty.model;

import org.eclipse.jetty.server.Handler;

import java.util.*;

import static com.vegaasen.http.jetty.container.ContainerProperties.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class JettyArguments {

    private String[] protocols = JETTY_PROTOCOLS;
    private String rootPath = DEFAULT_PATH;
    private String controlRoot = DEFAULT_PATH;
    private String contextPath = DEFAULT_CONTEXT_PATH;
    private int httpPort = DEFAULT_PORT;
    private String webAppResourceFolder = DEFAULT_WEBAPP_RESOURCE;
    private HttpsConfiguration httpsConfiguration;
    private Authentication authentication;
    private Map<String, String> initProperties = new HashMap<String, String>();
    private List<Handler> handlers = new ArrayList<Handler>();

    public JettyArguments() {
    }

    public String[] getProtocols() {
        return protocols;
    }

    public void setProtocols(String[] protocols) {
        this.protocols = protocols;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public String getControlRoot() {
        return controlRoot;
    }

    public void setControlRoot(String controlRoot) {
        this.controlRoot = controlRoot;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getWebAppResourceFolder() {
        return webAppResourceFolder;
    }

    public void setWebAppResourceFolder(String webAppResourceFolder) {
        this.webAppResourceFolder = webAppResourceFolder;
    }

    public HttpsConfiguration getHttpsConfiguration() {
        return httpsConfiguration;
    }

    public void setHttpsConfiguration(HttpsConfiguration httpsConfiguration) {
        this.httpsConfiguration = httpsConfiguration;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Map<String, String> getInitProperties() {
        return initProperties;
    }

    public void addInitProperty(String propertyKey, String propertyValue) {
        initProperties.put(propertyKey, propertyValue);
    }

    public boolean hasProperties() {
        return initProperties != null && !initProperties.isEmpty();
    }

    public List<Handler> getHandlers() {
        return handlers;
    }

    public void addHandler(final Handler handler) {
        handlers.add(handler);
    }

    public String printRequestableUrls() {
        StringBuilder builder = new StringBuilder();
        builder.append("http://localhost:").append(getHttpPort()).append(getContextPath());
        if (getHttpsConfiguration() != null) {
            builder.append("\nhttps://localhost:").append(getHttpsConfiguration().getHttpsPort()).append(getContextPath());
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "JettyArguments{" +
                "protocols=" + Arrays.toString(protocols) +
                ", rootPath='" + rootPath + '\'' +
                ", controlRoot='" + controlRoot + '\'' +
                ", contextPath='" + contextPath + '\'' +
                ", httpPort=" + httpPort +
                ", webAppResourceFolder='" + webAppResourceFolder + '\'' +
                ", httpsConfiguration=" + httpsConfiguration +
                '}';
    }
}
