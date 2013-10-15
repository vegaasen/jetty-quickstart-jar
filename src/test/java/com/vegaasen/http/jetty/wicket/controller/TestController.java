package com.vegaasen.http.jetty.wicket.controller;

import com.vegaasen.http.jetty.wicket.page.TestPage;
import org.apache.wicket.Page;
import org.apache.wicket.core.util.file.WebApplicationPath;
import org.apache.wicket.protocol.http.WebApplication;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class TestController extends WebApplication {

    @Override
    public void init() {
        super.init();
        getResourceSettings().getResourceFinders().add(new WebApplicationPath(getServletContext(), "/html"));
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return TestPage.class;
    }
}
