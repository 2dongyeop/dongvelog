package com.dongvelog.domain.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public String main() {
        return "main page";
    }

    @GetMapping("/user")
    public String user() {
        return "user page";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin page";
    }
}
