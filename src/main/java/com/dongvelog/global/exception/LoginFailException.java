package com.dongvelog.global.exception;

public class LoginFailException extends DongvelogException {

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public LoginFailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
