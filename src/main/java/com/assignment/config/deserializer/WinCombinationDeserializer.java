package com.assignment.config.deserializer;

import com.assignment.config.Config.WinCombination;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WinCombinationDeserializer extends StdDeserializer<Map<String, WinCombination>> {

    public WinCombinationDeserializer() {
        this(null);
    }

    public WinCombinationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Map<String, WinCombination> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectCodec codec = jp.getCodec();
        JsonNode node = codec.readTree(jp);
        Map<String, WinCombination> winCombinations = new HashMap<>();

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String key = field.getKey();
            WinCombination winCombination = codec.treeToValue(field.getValue(), WinCombination.class);
            // Set the name of the WinCombination entity into entity's field
            winCombination.setName(key);
            winCombinations.put(key, winCombination);
        }

        return winCombinations;
    }
}