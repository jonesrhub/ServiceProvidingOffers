package com.worldpay.offers.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Custom deserializer class to allow jackson to deserialize LocalDateTime to specified format.
 */
public class LocalDateDeserializer extends StdDeserializer<LocalDateTime> {

    protected LocalDateDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return LocalDateTime.parse(jsonParser.readValueAs(String.class),
            DateTimeFormatter.ISO_LOCAL_DATE_TIME).truncatedTo(ChronoUnit.SECONDS);
    }


}
