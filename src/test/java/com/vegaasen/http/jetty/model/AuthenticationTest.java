package com.vegaasen.http.jetty.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class AuthenticationTest {

    @Test
    public void shouldSuccessfullyAddStar() {
        Authentication authentication = new Authentication();
        authentication.setProtectedPath("/protected/");
        assertNotNull(authentication.getProtectedPath());
        assertTrue(authentication.getProtectedPath().endsWith("*"));
    }

    @Test
    public void shouldSuccessfullyAddSlashAndStar() {
        Authentication authentication = new Authentication();
        authentication.setProtectedPath("/protected");
        assertNotNull(authentication.getProtectedPath());
        assertTrue(authentication.getProtectedPath().endsWith("*"));
        assertTrue(authentication.getProtectedPath().endsWith("/*"));
    }

    @Test
    public void shouldAddShitloadsOfStuff() {
        Authentication authentication = new Authentication();
        authentication.setProtectedPath("");
        assertNotNull(authentication.getProtectedPath());
        assertTrue(authentication.getProtectedPath().endsWith("/*"));
        assertEquals("/*", authentication.getProtectedPath());
    }

}
