package com.example.coursecanvasspring.helper;

import java.util.Map;

public class RequestValidators {
    public static Boolean validateRequestKeys(String[] keys, Map<String,String> req) {
        for (String key : keys) {
            if (!req.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
}
