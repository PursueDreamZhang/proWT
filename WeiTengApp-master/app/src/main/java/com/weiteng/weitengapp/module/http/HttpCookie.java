package com.weiteng.weitengapp.module.http;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Admin on 2017/1/13.
 */

public class HttpCookie extends HashMap<String, String> {
    public static HttpCookie parseString(String data) {
        HttpCookie cookie = new HttpCookie();
        String[] cookies = data.split(";");
        for (String cookie_ : cookies) {
            int pos = cookie_.indexOf('=');
            if (pos == -1) {
                cookie.put(cookie_, "");
            } else {
                String key = cookie_.substring(0, pos);
                String val = cookie_.substring(pos + 1);
                cookie.put(key, val);
            }
        }

        return cookie;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Set<String> keySet = keySet();
        for (String key : keySet) {
            if ("".equals(get(key))) {
                builder.append(key);
            } else {
                builder.append(key + "=" + get(key));
            }
        }

        return builder.toString();
    }

    public String getKVString(String key) {
        String val = get(key);
        if (val == null) {
            return null;
        } else {
            return key + "=" + get(key);
        }
    }
}
