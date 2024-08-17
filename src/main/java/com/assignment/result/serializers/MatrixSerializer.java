package com.assignment.result.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Arrays;

public class MatrixSerializer extends JsonSerializer<String[][]> {

    /**
     * Overrides the default serialization of a matrix to a JSON string in order to format it properly.
     * Example of formatted matrix:
     * <p>
     * "matrix" : [
     * ["E", "D", "D"],
     * ["D", "D", "A"],
     * ["C", "5x", "C"]
     * ],
     */
    @Override
    public void serialize(String[][] matrix, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String matrixAsString = Arrays.deepToString(matrix);
        String formattedMatrix = matrixAsString.replaceFirst("\\[\\[", "[\n    [")
                .replaceFirst("]]", "]\n]")
                .replaceAll("], \\[", "],\n    [")
                .replaceAll("([a-zA-Z0-9+]+)", "\"$1\"");

        gen.writeRawValue(formattedMatrix);
    }
}
