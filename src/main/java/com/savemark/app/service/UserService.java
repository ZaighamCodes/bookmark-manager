package com.savemark.app.service;

import com.savemark.app.dto.RegistrationRequest;
import com.savemark.app.models.User;
import com.savemark.app.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    public User registerUser(RegistrationRequest request)
    {
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        return userRepo.save(user);
    }
}
