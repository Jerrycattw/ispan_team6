package com.util;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class StringAdapter extends TypeAdapter<String> {

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        if (value == null) {
            out.value(""); // 將 null 轉換為空字串
        } else {
            out.value(value);
        }
    }

    @Override
    public String read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return ""; // 將 null 轉換為空字串
        }
        return in.nextString();
    }
}
