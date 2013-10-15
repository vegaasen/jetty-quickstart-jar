package com.vegaasen.http.jetty.utils;

import com.vegaasen.http.jetty.storage.model.Thing;
import com.vegaasen.http.jetty.storage.model.Things;

import java.util.UUID;

/**
 * @author <a href="vegard.aasen@gmail.com">vegardaasen</a>
 */
public final class PatheticRandomUtils {

    private PatheticRandomUtils() {
    }

    public static Things getSomeRandomThings() {
        Things rndThings = new Things();
        rndThings.setId(Long.parseLong(someNumber()));
        int c = 0;
        for (; ; ) {
            rndThings.addThing(
                    new Thing.Builder()
                            .id(someNumber())
                            .name(UUID.randomUUID().toString())
                            .build());
            if (c != 10) {
                c++;
            } else {
                break;
            }
        }
        return rndThings;
    }

    public static String someNumber() {
        return Integer.toString(5 + (int) (Math.random() * ((10 - 5) + 1)));
    }

}
