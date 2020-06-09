package com.worldpay.offers.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Custom serializer class to allow jackson to serialize LocalDateTime to specified format
 */
public class LocalDateSerializer extends StdSerializer<LocalDateTime> {

    protected LocalDateSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime.truncatedTo(ChronoUnit.SECONDS)));
    }
}
