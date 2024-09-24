package com.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class CustomSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeString("");
        } else if (value instanceof Number) {
            // 对于数值类型，0 转换为 ""
            Number numberValue = (Number) value;
            if (numberValue.intValue() == 0) {
                gen.writeString("");
            } else {
                gen.writeNumber(numberValue.intValue());
            }
        } else if (value instanceof String) {
            // 对于字符串类型，null 转换为 ""
            String stringValue = (String) value;
            if (stringValue.isEmpty()) {
                gen.writeString("");
            } else {
                gen.writeString(stringValue);
            }
        } else if (value instanceof LocalDate) {
            // 对于 LocalDate 类型，null 转换为 ""
            LocalDate dateValue = (LocalDate) value;
            gen.writeString(dateValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        } else {
            gen.writeObject(value);  // 其他类型按原样处理
        }
    }
}
