package com.assignment.config.enums;

import lombok.Getter;

@Getter
public enum WinCombinationGroupType {
    SAME_SYMBOLS("same_symbols"),
    HORIZONTALLY_LINEAR_SYMBOLS("horizontally_linear_symbols"),
    VERTICALLY_LINEAR_SYMBOLS("vertically_linear_symbols"),
    LTR_DIAGONALLY_LINEAR_SYMBOLS("ltr_diagonally_linear_symbols"),
    RTL_DIAGONALLY_LINEAR_SYMBOLS("rtl_diagonally_linear_symbols");

    private final String value;

    WinCombinationGroupType(String value) {
        this.value = value;
    }

    public static WinCombinationGroupType fromString(String text) {
        for (WinCombinationGroupType b : WinCombinationGroupType.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}