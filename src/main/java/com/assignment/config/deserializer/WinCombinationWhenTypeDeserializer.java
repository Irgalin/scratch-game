package com.assignment.config.deserializer;

import com.assignment.config.enums.WinCombinationWhenType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class WinCombinationWhenTypeDeserializer extends JsonDeserializer<WinCombinationWhenType> {

    @Override
    public WinCombinationWhenType deserialize(JsonParser p, DeserializationContext context) throws IOException {
        return WinCombinationWhenType.fromString(p.getText());
    }

}