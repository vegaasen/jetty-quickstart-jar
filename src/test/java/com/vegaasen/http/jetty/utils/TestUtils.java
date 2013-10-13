package com.vegaasen.http.jetty.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;

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

    public static InputStream streamFromHttpPort(final int port, String path) throws IOException {
        return streamFromHttpPort(port, path, false);
    }

    public static InputStream streamFromHttpPort(final int port, String path, final boolean errorStream) throws IOException {
        HttpURLConnection connection = getConnection(port, path);
        if (connection != null) {
            if (errorStream) {
                return connection.getErrorStream();
            } else {
                return connection.getInputStream();
            }
        }
        throw new IllegalArgumentException("Shit happened");
    }

    public static void getCookiesFromRequest(int port, String path) throws IOException {
        HttpURLConnection connection = getConnection(port, path);
        if (connection != null) {
            connection.getInputStream();
        }
        throw new IllegalArgumentException("Shit happened");
    }

    private static HttpURLConnection getConnection(int port, String path) throws IOException {
        if (path == null) {
            path = "/";
        }
        URL u = new URL(String.format("http://127.0.0.1:%s%s", port, path));
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        return connection;
    }

}
