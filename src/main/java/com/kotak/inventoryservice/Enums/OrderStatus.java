package com.kotak.inventoryservice.Enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
    PENDING("PENDING"),

    COMPLETED("COMPLETED"),

    CANCELED("CANCELED"),

    ACCEPTED("ACCEPTED"),

    REJECTED("REJECTED");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
