package com.vegaasen.http.jetty.spring.rest.controller;

import com.vegaasen.http.jetty.storage.SimpleStorage;
import com.vegaasen.http.jetty.storage.model.Things;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
@Controller("Simple MVC TestController in Spring")
@RequestMapping(
        value = "/test"
)
public final class TestController {

    public static final String DEFAULT_SIMPLE_RESPONSE = "Ello!:)";

    private static final String DEFAULT_PRODUCES = "text/html;charset=utf-8;";

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = DEFAULT_PRODUCES
    )
    public
    @ResponseBody
    String def() {
        return "I'm just a poor servlet :-(";
    }

    @RequestMapping(
            value = "simpleTest",
            produces = DEFAULT_PRODUCES,
            method = {RequestMethod.GET})
    public
    @ResponseBody
    String uberSimpleTest() {
        return DEFAULT_SIMPLE_RESPONSE;
    }

    @RequestMapping(
            value = "simpleTest",
            produces = DEFAULT_PRODUCES
    )
    public
    @ResponseBody
    String uberSimpleTestWithPost(
            @RequestParam(value = "thing", defaultValue = "", required = true) final String thing,
            final HttpServletResponse response
    ) {
        if (thing == null || thing.isEmpty()) {
            throw new IllegalArgumentException("Unable to do something with the thing.");
        }
        final long timeStamp = System.currentTimeMillis();
        SimpleStorage.INSTANCE.addThing(
                timeStamp,
                thing);
        response.setDateHeader("Last-Modified", timeStamp);
        return Long.toString(timeStamp);
    }

    @RequestMapping(
            value = "allThings",
            produces = DEFAULT_PRODUCES
    )
    public
    @ResponseBody
    Things getAllThings() {
        return SimpleStorage.INSTANCE.getThings();
    }

    @RequestMapping(
            value = "lastAddedThing",
            produces = DEFAULT_PRODUCES
    )
    public
    @ResponseBody
    String getLastAddedThing() {
        return SimpleStorage.INSTANCE.getLastAddedThing().getId();
    }

}
