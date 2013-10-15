package com.vegaasen.http.jetty.utils;

import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class TestUtils {

    private TestUtils() {
    }

    public static String stringFromInputStream(final InputStream inputStream) throws IOException {
        StringBuilder result = new StringBuilder();
        if (inputStream != null && inputStream.available() > 0) {
            int status;
            while ((status = inputStream.read()) != -1) {
                result.append((char) status);
            }
        }
        return result.toString();
    }

    public static boolean available(int port) throws IOException {
        ServerSocket ss;
        DatagramSocket ds;
        ss = new ServerSocket(port);
        ss.setReuseAddress(true);
        ds = new DatagramSocket(port);
        ds.setReuseAddress(true);
        return true;
    }

    public static InputStream streamFromProtectedHttpPort(
            final int port,
            String path,
            final String username,
            final String password
    ) throws IOException {
        Authenticator.setDefault(new PwdAuthenticator(username, password));
        HttpURLConnection connection = getConnection(port, path);
        if (connection != null) {
            return connection.getInputStream();
        }
        throw new IllegalArgumentException("Shit happened");
    }

    public static InputStream streamFromHttpPort(final int port, final String path) throws IOException {
        return streamFromHttpPort(port, path, false);
    }

    public static InputStream streamFromHttpPort(final int port, String path, final boolean errorStream)
            throws IOException {
        return streamFromHttpPort(port, path, errorStream, false);
    }

    public static InputStream streamFromHttpPort(final int port, String path, final boolean errorStream, final boolean usePost)
            throws IOException {
        HttpURLConnection connection = getConnection(port, path);
        if (usePost) {
            connection.setRequestMethod("POST");
        }
        if (connection != null) {
            if (errorStream) {
                return connection.getErrorStream();
            } else {
                return connection.getInputStream();
            }
        }
        throw new IllegalArgumentException("Shit happened");
    }

    public static Map<String, List<String>> getHeaders(final int port, String path, final boolean usePostt) throws IOException {
        HttpURLConnection connection = getConnection(port, path);
        if (connection != null) {
            return connection.getHeaderFields();
        }
        return Collections.emptyMap();
    }

    public static void getCookiesFromRequest(int port, String path) throws IOException {
        HttpURLConnection connection = getConnection(port, path);
        if (connection != null) {
            connection.getInputStream();
        }
        throw new IllegalArgumentException("Shit happened");
    }

    public static ServletHolder getSpringServletHolder() {
        final ServletHolder holder = new ServletHolder(new DispatcherServlet());
        holder.setInitParameter("contextConfigLocation", "classpath:/applicationContext.xml");
        holder.setInitOrder(1);
        return holder;
    }

    private static HttpURLConnection getConnection(int port, String path) throws IOException {
        if (path == null) {
            path = "/";
        }
        URL u = new URL(String.format("http://127.0.0.1:%s%s", port, path));
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        return connection;
    }

    static class PwdAuthenticator extends Authenticator {

        private String user;
        private String password;

        PwdAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication(user, password.toCharArray()));
        }
    }

}
