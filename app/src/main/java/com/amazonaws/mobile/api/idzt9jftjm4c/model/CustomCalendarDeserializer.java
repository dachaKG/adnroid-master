package com.amazonaws.mobile.api.idzt9jftjm4c.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomCalendarDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        String dateAsString = jsonparser.getText();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(dateAsString);
            //CustomCalendarSerializer.FORMATTER.parse(dateAsString);
//            Date calendar = Date.getInstance(
////                    CustomCalendarSerializer.LOCAL_TIME_ZONE,
////                    CustomCalendarSerializer.LOCALE_HUNGARIAN
////            );
//            calendar.setTime(date);
            return date;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
