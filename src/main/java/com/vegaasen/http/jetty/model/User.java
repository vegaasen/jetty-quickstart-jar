package com.vegaasen.http.jetty.model;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class User {

    private String username;
    private String password;

    private User(final Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {

        private String username;
        private String password;

        public Builder username(final String s) {
            this.username = s;
            return this;
        }

        public Builder password(final String s) {
            this.password = s;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

}
