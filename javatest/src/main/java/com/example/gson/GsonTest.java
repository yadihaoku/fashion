package com.example.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.HashMap;

/**
 * Created by YanYadi on 2017/7/25.
 */
public class GsonTest {
    static final String json = "adsf{\"code\":10002,\"msg\":\"\\u6570\\u636e\\u7b7e\\u540d\\u9519\\u8bef\",\"data\":[],\"time\":1500910383,\"runtime\":\"0.0106s\"}";
    public static void main(String[] args) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        System.out.println(jsonElement.getAsJsonObject().get("data").toString());
        System.out.println(jsonElement.getAsJsonObject().get("code").getAsString());
        HashMap<String,String> map =  gson.fromJson(jsonElement,    HashMap.class);
        System.out.println(map);
    }
}
