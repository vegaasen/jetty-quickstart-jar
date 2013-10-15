package com.vegaasen.http.jetty.wicket.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.io.Serializable;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class TestPage extends WebPage implements Serializable {

    public static final String DEFAULT_OUTPUT_HTML = "Ello!:)";

    private static final long serialVersionUID = 1L;

    public TestPage(final PageParameters parameters) {
        add(new Label("testmsg", DEFAULT_OUTPUT_HTML));
    }

}
