package com.mct.autotask.utils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonHelper {

    public static String objToJson(Object o) {
        return new Gson().toJson(o);
    }

    public static <T> T jsonToObj(String strJson, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(strJson, clazz);
    }

    @NonNull
    public static <T> List<T> jsonToList(String strJson, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(strJson);
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), clazz));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @NonNull
    public static <T> Set<T> jsonToSet(String strJson, Class<T> clazz) {
        Set<T> list = new HashSet<>();
        try {
            JSONArray jsonArray = new JSONArray(strJson);
            Gson gson = new Gson();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(gson.fromJson(jsonArray.getJSONObject(i).toString(), clazz));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
