package com.enzulode.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.Instant;

/** Custom Instant type json serializer implementation. */
public class JsonInstantSerializer extends JsonSerializer<Instant> {

    /**
     * This method performs instant instance serialization.
     *
     * @param value instant value
     * @param generator json generator instance
     * @param provider serializer provider instance
     * @throws IOException if serialization goes wrong
     */
    @Override
    public void serialize(Instant value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        if (value == null) {
            generator.writeNull();
            return;
        }

        generator.writeNumber(value.getEpochSecond());
    }
}
