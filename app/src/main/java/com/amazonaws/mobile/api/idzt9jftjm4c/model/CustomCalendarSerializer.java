package com.amazonaws.mobile.api.idzt9jftjm4c.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomCalendarSerializer extends JsonSerializer<Date> {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
//    public static final Locale LOCALE_HUNGARIAN = new Locale("hu", "HU");
//    public static final TimeZone LOCAL_TIME_ZONE = TimeZone.getTimeZone("Europe/Budapest");

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        if (value == null) {
            gen.writeNull();
        } else {
            gen.writeString(FORMATTER.format(value.getTime()));
        }
    }
}