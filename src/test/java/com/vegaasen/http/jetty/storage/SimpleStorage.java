package com.vegaasen.http.jetty.storage;

import com.vegaasen.http.jetty.storage.model.Thing;
import com.vegaasen.http.jetty.storage.model.Things;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public enum SimpleStorage {

    INSTANCE;

    private static final String EMPTY = "";

    private static Things things = new Things(System.currentTimeMillis());

    public void addThing(long timeStamp, String whatevz) {
        things.addThing(new Thing.Builder().id(Long.toString(timeStamp)).name(whatevz).build());
    }

    public Thing getThingByTimestamp(long timeStamp) {
        if (things.getThings().isEmpty()) {
            return null;
        }
        for (Thing thing : things.getThings()) {
            if (thing.getId().equals(Long.toString(timeStamp))) {
                return thing;
            }
        }
        return null;
    }

    public Things getThings() {
        return things;
    }

    public Thing getLastAddedThing() {
        if (things.getThings() != null && !things.getThings().isEmpty()) {
            return things.getThings().get(things.getThings().size() - 1);
        }
        return null;
    }

    public void clearAllThings() {
        things.setId(System.currentTimeMillis());
        if (things.getThings().isEmpty()) {
            return;
        }
        things.getThings().clear();
    }

}
