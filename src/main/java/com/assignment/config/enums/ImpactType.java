package com.assignment.config.enums;

import lombok.Getter;

@Getter
public enum ImpactType {
    MULTIPLY_REWARD("multiply_reward"),
    EXTRA_BONUS("extra_bonus"),
    MISS("miss");

    private final String value;

    ImpactType(String value) {
        this.value = value;
    }

    public static ImpactType fromString(String text) {
        for (ImpactType b : ImpactType.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}