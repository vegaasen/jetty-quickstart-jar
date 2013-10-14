package com.vegaasen.http.jetty.jersey.rest.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
@XmlRootElement(name = "crap")
public final class Things {

    private long id;
    public List<Thing> things = new ArrayList<Thing>();

    public Things() {
    }

    @XmlElement(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Thing> getThings() {
        return things;
    }

    public void addThing(Thing thing) {
        this.things.add(thing);
    }
}
