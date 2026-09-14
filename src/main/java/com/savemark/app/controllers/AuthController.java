package com.savemark.app.controllers;

import com.savemark.app.dto.RegistrationRequest;
import com.savemark.app.models.User;
import com.savemark.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public User registerUser(@RequestBody RegistrationRequest request)
    {
        System.out.println(request);
        return userService.registerUser(request);
    }
}
