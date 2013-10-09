package com.vegaasen.http.jetty.container;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public enum JettyProperties {

    CHARSET("org.eclipse..", "");

    private String key;
    private String value;

    private JettyProperties(String sk, String sv) {
        this.key = sk;
        this.value = sv;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return value;
    }

    public static JettyProperties getJettyPropertyByKey(final String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Unable to find property by null/empty key");
        }
        for (JettyProperties j : values()) {
            if (j.getKey().equals(key)) {
                return j;
            }
        }
        throw new IllegalArgumentException(String.format("JettyProperty by key {%s} does not exists", key));
    }

    @Override
    public String toString() {
        return "JettyProperties{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
