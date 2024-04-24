package com.kotak.inventoryservice.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> sendResponse(String message, HttpStatus status){
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        return new ResponseEntity<Object>(map, status);
    }
    public static ResponseEntity<Object> sendResponse(String message, HttpStatus status, Object respObj){
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", respObj);

        return new ResponseEntity<Object>(map, status);
    }
}
