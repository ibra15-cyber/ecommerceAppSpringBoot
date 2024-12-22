package com.ibra.ecommercePractice.controller;

import com.ibra.ecommercePractice.dto.LoginRequest;
import com.ibra.ecommercePractice.dto.Response;
import com.ibra.ecommercePractice.dto.UserDto;
import com.ibra.ecommercePractice.service.interf.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody UserDto registerRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(loginRequest));
    }
}
