package com.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class IntegerAdapter extends TypeAdapter<Integer> {

    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        if (value == null || value == 0) {
            out.value(""); // 將 null 或 0 轉換為空字串
        } else {
            out.value(value);
        }
    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null; // 將 null 轉換為 null
        }
        String stringValue = in.nextString();
        if (stringValue.isEmpty()) {
            return 0; // 空字串轉換為 0
        }
        return Integer.parseInt(stringValue);
    }
}

