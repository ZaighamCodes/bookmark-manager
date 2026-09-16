package com.savemark.app.service;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService{


    public ResponseEntity<?> getCurrentUser(Authentication authentication)
    {
        String username=authentication.getName();
        return ResponseEntity.ok(
                Map.of(
                        "username",username
                )
        );
    }
}