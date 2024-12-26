package com.ibra.ecommercePractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Oauth2Controller {

    @GetMapping("/")
    public String home() {
        return "this is the home page that is excluded from allowed pages";
    }

    @GetMapping("/secure")
    public String secure(){
        return "this is a secure route";
    }
}
