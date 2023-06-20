package com.dongvelog.global.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public abstract class DongvelogException extends RuntimeException {

    @Getter
    private final Map<String, String> validation = new HashMap<>();

    public DongvelogException(final String message) {
        super(message);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
