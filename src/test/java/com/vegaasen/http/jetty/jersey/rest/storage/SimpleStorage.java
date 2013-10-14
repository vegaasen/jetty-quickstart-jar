package com.vegaasen.http.jetty.jersey.rest.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public enum SimpleStorage {

    INSTANCE;

    private static final String EMPTY = "";

    private static Map<Long, String> things = new HashMap<Long, String>();

    public void addThing(long timeStamp, String whatevz) {
        things.put(timeStamp, whatevz);
    }

    public String getThingByTimestamp(long timeStamp) {
        if (things.isEmpty() || !things.containsKey(timeStamp)) {
            return EMPTY;
        }
        return things.get(timeStamp);
    }

    public Map<Long, String> getThings() {
        return things;
    }

    public void clearAllThings() {
        if (things.isEmpty()) {
            return;
        }
        things.clear();
    }

}
