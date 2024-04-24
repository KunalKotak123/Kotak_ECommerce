package com.kotak.inventoryservice.Exception;

public class UnknownOrderStatusException extends RuntimeException {
    public UnknownOrderStatusException(String message) {
        super(message);
    }
}
