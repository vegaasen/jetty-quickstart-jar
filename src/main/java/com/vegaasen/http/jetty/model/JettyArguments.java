package com.vegaasen.http.jetty.model;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ErrorPageErrorHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.*;

import static com.vegaasen.http.jetty.container.ContainerDefaults.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class JettyArguments {

    private String[] protocols = JETTY_PROTOCOLS;
    private String rootPath = DEFAULT_PATH;
    private String contextPath = DEFAULT_CONTEXT_PATH;
    private int httpPort = DEFAULT_PORT;
    private String webAppResourceFolder = DEFAULT_WEBAPP_RESOURCE;
    private HttpsConfiguration httpsConfiguration;
    private Authentication authentication;
    private Map<String, String> initProperties = new HashMap<String, String>();
    private List<Handler> handlers = new ArrayList<Handler>();
    private SessionHandler sessionHandler = new SessionHandler();
    private ErrorHandler errorHandler = new ErrorPageErrorHandler();
    private List<EventListener> requestListeners = new ArrayList<EventListener>();
    private List<ServletHolder> servlets = new ArrayList<ServletHolder>();
    private ControlServlet controlServlet = new ControlServlet();

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

    public List<EventListener> getRequestListeners() {
        return requestListeners;
    }

    public void addRequestListener(final EventListener requestListener) {
        requestListeners.add(requestListener);
    }

    public ErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public SessionHandler getSessionHandler() {
        return sessionHandler;
    }

    public void setSessionHandler(SessionHandler sessionHandler) {
        this.sessionHandler = sessionHandler;
    }

    public List<ServletHolder> getServlets() {
        return servlets;
    }

    public void addServlet(final ServletHolder holder) {
        servlets.add(holder);
    }

    public ControlServlet getControlServlet() {
        return controlServlet;
    }

    public void setControlServlet(ControlServlet controlServlet) {
        this.controlServlet = controlServlet;
    }

    public String printRequestableUrls() {
        StringBuilder builder = new StringBuilder();
        builder.append("http://localhost:").append(getHttpPort()).append(getContextPath());
        if (getHttpsConfiguration() != null) {
            builder.append("\nhttps://localhost:").append(getHttpsConfiguration().getHttpsPort()).append(getContextPath());
        }
        if (getControlServlet() != null) {
            builder.append("\nhttps://localhost:").append(getControlServlet().getHttpControlPort()).append(getControlServlet().getControlPath());
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JettyArguments arguments = (JettyArguments) o;

        if (httpPort != arguments.httpPort) return false;
        if (authentication != null ? !authentication.equals(arguments.authentication) : arguments.authentication != null)
            return false;
        if (contextPath != null ? !contextPath.equals(arguments.contextPath) : arguments.contextPath != null)
            return false;
        if (controlServlet != null ? !controlServlet.equals(arguments.controlServlet) : arguments.controlServlet != null)
            return false;
        if (errorHandler != null ? !errorHandler.equals(arguments.errorHandler) : arguments.errorHandler != null)
            return false;
        if (handlers != null ? !handlers.equals(arguments.handlers) : arguments.handlers != null) return false;
        if (httpsConfiguration != null ? !httpsConfiguration.equals(arguments.httpsConfiguration) : arguments.httpsConfiguration != null)
            return false;
        if (initProperties != null ? !initProperties.equals(arguments.initProperties) : arguments.initProperties != null)
            return false;
        if (!Arrays.equals(protocols, arguments.protocols)) return false;
        if (requestListeners != null ? !requestListeners.equals(arguments.requestListeners) : arguments.requestListeners != null)
            return false;
        if (rootPath != null ? !rootPath.equals(arguments.rootPath) : arguments.rootPath != null) return false;
        if (servlets != null ? !servlets.equals(arguments.servlets) : arguments.servlets != null) return false;
        if (sessionHandler != null ? !sessionHandler.equals(arguments.sessionHandler) : arguments.sessionHandler != null)
            return false;
        if (webAppResourceFolder != null ? !webAppResourceFolder.equals(arguments.webAppResourceFolder) : arguments.webAppResourceFolder != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = protocols != null ? Arrays.hashCode(protocols) : 0;
        result = 31 * result + (rootPath != null ? rootPath.hashCode() : 0);
        result = 31 * result + (contextPath != null ? contextPath.hashCode() : 0);
        result = 31 * result + httpPort;
        result = 31 * result + (webAppResourceFolder != null ? webAppResourceFolder.hashCode() : 0);
        result = 31 * result + (httpsConfiguration != null ? httpsConfiguration.hashCode() : 0);
        result = 31 * result + (authentication != null ? authentication.hashCode() : 0);
        result = 31 * result + (initProperties != null ? initProperties.hashCode() : 0);
        result = 31 * result + (handlers != null ? handlers.hashCode() : 0);
        result = 31 * result + (sessionHandler != null ? sessionHandler.hashCode() : 0);
        result = 31 * result + (errorHandler != null ? errorHandler.hashCode() : 0);
        result = 31 * result + (requestListeners != null ? requestListeners.hashCode() : 0);
        result = 31 * result + (servlets != null ? servlets.hashCode() : 0);
        result = 31 * result + (controlServlet != null ? controlServlet.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JettyArguments{" +
                "protocols=" + Arrays.toString(protocols) +
                ", rootPath='" + rootPath + '\'' +
                ", contextPath='" + contextPath + '\'' +
                ", httpPort=" + httpPort +
                ", webAppResourceFolder='" + webAppResourceFolder + '\'' +
                ", httpsConfiguration=" + httpsConfiguration +
                ", authentication=" + authentication +
                ", initProperties=" + initProperties +
                ", handlers=" + handlers +
                ", sessionHandler=" + sessionHandler +
                ", errorHandler=" + errorHandler +
                ", requestListeners=" + requestListeners +
                ", servlets=" + servlets +
                ", controlServlet=" + controlServlet +
                '}';
    }

}
