package com.example;

import com.google.gson.Gson;


public class Adventure {
    private static final String readJson = Data.getFileContentsAsString("AdventuresJSON");
    Layout parsedJson;

    public Layout parsingJson() {
        Gson gson = new Gson();
        parsedJson = gson.fromJson(readJson, Layout.class);
        return parsedJson;
    }


}
