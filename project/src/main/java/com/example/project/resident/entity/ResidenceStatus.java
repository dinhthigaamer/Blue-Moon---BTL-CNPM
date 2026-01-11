package com.example.project.resident.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ResidenceStatus {
    PERMANENT_RESIDENCE("Thường trú"),
    TEMPORARY_RESIDENCE("Tạm trú"),
    TEMPORARY_ABSENCE("Tạm vắng");

    private final String label;

    ResidenceStatus(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    @JsonCreator
    public static ResidenceStatus fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (ResidenceStatus status : values()) {
            if (status.name().equalsIgnoreCase(value) || status.label.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Giá trị residenceStatus không hợp lệ: " + value);
    }
}
