package com.vegaasen.http.jetty.storage.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
@XmlRootElement(name = "thing")
public final class Thing {

    private String id;
    private String name;

    public Thing() {
    }

    private Thing(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    @XmlAttribute(name = "id")
    public String getId() {
        return id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public static class Builder {

        private String id;
        private String name;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Thing build() {
            return new Thing(this);
        }

    }
}
