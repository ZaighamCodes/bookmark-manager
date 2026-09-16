package com.savemark.app.controllers;

import com.savemark.app.dto.LoginRequest;
import com.savemark.app.dto.RegistrationRequest;
import com.savemark.app.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //    for registering our new user
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegistrationRequest request)
    {
        System.out.println(request);
        return authService.registerUser(request);
    }

//    for logging in
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   HttpServletRequest httpRequest,
                                   HttpServletResponse httpResponse)
    {
       return authService.loginUser(request,httpRequest,httpResponse);
    }
}