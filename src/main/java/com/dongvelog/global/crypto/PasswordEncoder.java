package com.dongvelog.global.crypto;

public interface PasswordEncoder {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encryptedPassword);
}
