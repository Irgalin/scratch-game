package com.assignment.config.deserializer;

import com.assignment.config.enums.ImpactType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ImpactTypeDeserializer extends JsonDeserializer<ImpactType> {

    @Override
    public ImpactType deserialize(JsonParser p, DeserializationContext context) throws IOException {
        return ImpactType.fromString(p.getText());
    }

}