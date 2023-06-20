package com.dongvelog.global.exception;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private final Map<String, String> validation;

    @Builder
    public ErrorResponse(final String code, final String message, final Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }

    public void addValidation(final String fieldName, final String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ErrorResponse) obj;
        return Objects.equals(this.code, that.code) &&
                Objects.equals(this.message, that.message) &&
                Objects.equals(this.validation, that.validation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, validation);
    }

    @Override
    public String toString() {
        return "ErrorResponse[" +
                "code=" + code + ", " +
                "message=" + message + ", " +
                "validation=" + validation + ']';
    }
}
