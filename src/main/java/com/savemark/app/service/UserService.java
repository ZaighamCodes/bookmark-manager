package com.savemark.app.service;

import com.savemark.app.dto.RegistrationRequest;
import com.savemark.app.models.User;
import com.savemark.app.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    public ResponseEntity<Map<String, String>> registerUser(RegistrationRequest request)
    {
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","User registered"));
    }
}
