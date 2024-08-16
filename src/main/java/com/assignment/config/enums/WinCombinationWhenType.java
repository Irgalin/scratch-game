package com.assignment.config.enums;

import lombok.Getter;

@Getter
public enum WinCombinationWhenType {

    SAME_SYMBOLS("same_symbols"),
    LINEAR_SYMBOLS("linear_symbols");

    private final String value;

    WinCombinationWhenType(String value) {
        this.value = value;
    }

    public static WinCombinationWhenType fromString(String text) {
        for (WinCombinationWhenType b : WinCombinationWhenType.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}