package com.domein.util;

import org.json.JSONObject;

public class JsonParser {

    public static String parseOcid(String jsonResponse) {
        JSONObject jsonObj = new JSONObject(jsonResponse);
        return jsonObj.getString("ocid");
    }
}
