package com.example.project.resident.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static Gender fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (Gender gender : values()) {
            if (gender.name().equalsIgnoreCase(value) || gender.label.equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Giá trị gender không hợp lệ: " + value);
    }
}
