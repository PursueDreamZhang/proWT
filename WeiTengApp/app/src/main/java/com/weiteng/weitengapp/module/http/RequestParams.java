package com.weiteng.weitengapp.module.http;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Admin on 2016/11/19.
 */

public class RequestParams extends HashMap<String, String> {
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Set<String> keys = this.keySet();
        for (String key : keys) {
            stringBuilder.append(key + "=" + get(key) + "&");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }
}
