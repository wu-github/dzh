package com.wurd.bd.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTool {
    private static Gson gson =
            new GsonBuilder().enableComplexMapKeySerialization().create();

    public static Gson get() {
        return gson;
    }
}
