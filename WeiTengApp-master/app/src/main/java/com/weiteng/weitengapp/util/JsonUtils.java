package com.weiteng.weitengapp.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static Object getValueFromKey(String json, String key) {
        return parseJsonToMap(json).get(key);
    }

    public static String parseBeanToJson(Object object, Type type) {
        return new Gson().toJson(object, type);
    }

    public static <T> T parseJsonToBean(String json, Class<T> cls) {
        return new Gson().fromJson(json, cls);
    }

    public static List<?> parseJsonToList(String json, Type type) {
        return new Gson().fromJson(json, type);
    }

    public static Map<String, Object> parseJsonToMap(String json) {
        return new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static String parseListToJson(List<?> list) {
        return new Gson().toJson(list);
    }

    public static String parseMapToJson(Map<?, ?> map) {
        return new Gson().toJson(map);
    }
}
