package com.assignment.config.deserializer;

import com.assignment.config.enums.WinCombinationGroupType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class WinCombinationGroupTypeDeserializer extends JsonDeserializer<WinCombinationGroupType> {

    @Override
    public WinCombinationGroupType deserialize(JsonParser p, DeserializationContext context) throws IOException {
        return WinCombinationGroupType.fromString(p.getText());
    }

}