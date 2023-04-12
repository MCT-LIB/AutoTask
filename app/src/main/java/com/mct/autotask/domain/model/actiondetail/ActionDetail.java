package com.mct.autotask.domain.model.actiondetail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mct.autotask.BuildConfig;
import com.mct.autotask.utils.JsonHelper;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class ActionDetail {

    private static final String PKG_NAME = BuildConfig.APPLICATION_ID;

    /**
     * Save the class name to parse from json
     */
    private final String className = getClass().getName().replace(PKG_NAME, "");

    public ActionDetail() {
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends ActionDetail> T fromJson(String json) {
        try {
            String className = new JSONObject(json).optString("className");
            String fullClassName = className.startsWith(PKG_NAME) ? className : PKG_NAME + className;
            return (T) JsonHelper.jsonToObj(json, Class.forName(fullClassName));
        } catch (JSONException | ClassNotFoundException e) {
            return null;
        }
    }

    public static <T extends ActionDetail> String toJson(T actionDetail) {
        return JsonHelper.objToJson(actionDetail);
    }

    @NonNull
    @Override
    public String toString() {
        return "ActionDetail{" +
                "className='" + className + '\'' +
                '}';
    }
}
