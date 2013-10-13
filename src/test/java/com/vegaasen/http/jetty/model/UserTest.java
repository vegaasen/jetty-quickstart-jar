package com.vegaasen.http.jetty.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class UserTest {

    private String userName;
    private String password;

    @Before
    public void setUp() {
        userName = "cool";
        password = "storyBro";
    }

    @Test
    public void shouldGenerateUser() {
        User user = new User.Builder().username(userName).password(password).build();
        assertNotNull(user);
        assertNotNull(user.getUsername());
        assertFalse(user.getUsername().isEmpty());
        assertEquals(userName, user.getUsername());
        assertNotNull(user.getPassword());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void shouldGenerateNulledObject() {
        User user = new User.Builder().build();
        assertNotNull(user);
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }

    @Test
    public void shouldSetOnlyUserName() {
        User user = new User.Builder().username(userName).build();
        assertNotNull(user);
        assertNotNull(user.getUsername());
        assertEquals(userName, user.getUsername());
        assertNull(user.getPassword());
    }

}
