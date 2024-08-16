package com.assignment.config.deserializer;

import com.assignment.config.enums.SymbolType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class SymbolTypeDeserializer extends JsonDeserializer<SymbolType> {

    @Override
    public SymbolType deserialize(JsonParser p, DeserializationContext context) throws IOException {
        return SymbolType.fromString(p.getText());
    }

}