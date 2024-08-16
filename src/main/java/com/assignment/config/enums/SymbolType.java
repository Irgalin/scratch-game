package com.assignment.config.enums;

import lombok.Getter;

@Getter
public enum SymbolType {
    STANDARD("standard"),
    BONUS("bonus");

    private final String value;

    SymbolType(String value) {
        this.value = value;
    }

    public static SymbolType fromString(String text) {
        for (SymbolType b : SymbolType.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}