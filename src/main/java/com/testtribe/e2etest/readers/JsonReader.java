package com.testtribe.e2etest.readers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    public static List<JsonObject> readJson(String jsonFilePath) {
        FileReader reader = null;
        try {
            reader = new FileReader(jsonFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JsonElement element = JsonParser.parseReader(reader);

        JsonArray array = element.getAsJsonArray();

        List<JsonObject> objectList = new ArrayList<JsonObject>();
        for (Object o : array) {
            JsonObject jsonObject = (JsonObject) o;

            objectList.add(jsonObject);
        }

        return objectList;
    }
}
