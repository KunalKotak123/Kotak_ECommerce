package com.kotak.inventoryservice.Exception;

import com.kotak.inventoryservice.Response.ResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UnknownOrderStatusException.class)
    public ResponseEntity<Object> handleUnknownOrderStatusException(UnknownOrderStatusException ex) {
        log.error("Unknown order status exception: {}", ex.getMessage(), ex);
        return ResponseHandler.sendResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}