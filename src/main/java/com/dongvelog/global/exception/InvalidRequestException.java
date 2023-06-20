package com.dongvelog.global.exception;

public class InvalidRequestException extends DongvelogException {

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequestException() {
        super(MESSAGE);
    }

    public InvalidRequestException(final String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }


    @Override
    public int getStatusCode() {
        return 400;
    }
}
