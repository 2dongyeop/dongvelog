package com.dongvelog.domain.auth.controller.request;

import lombok.Data;

@Data
public class SignUpRequest {

    private String name;
    private String password;
    private String email;
}
