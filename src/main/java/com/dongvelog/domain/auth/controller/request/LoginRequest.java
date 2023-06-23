package com.dongvelog.domain.auth.controller.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class LoginRequest {

    @NotBlank @Email
    private String email;

    @NotBlank
    private String password;
}
