package com.assignment.config;

import java.io.IOException;
import java.util.List;

public class InvalidConfigException extends IOException {
    public InvalidConfigException(List<String> errorMessages) {
        super("Invalid format of config file, the following errors are present:\n" + formatErrorMessages(errorMessages));
    }

    private static String formatErrorMessages(List<String> errorMessages) {
        StringBuilder formattedMessages = new StringBuilder();
        for (int i = 0; i < errorMessages.size(); i++) {
            formattedMessages.append(" ").append(i + 1).append(") ").append(errorMessages.get(i)).append("\n");
        }
        return formattedMessages.toString();
    }
}