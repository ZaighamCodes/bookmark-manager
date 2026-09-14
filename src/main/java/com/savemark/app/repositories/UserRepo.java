package com.savemark.app.repositories;

import com.savemark.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
       User findByUsername(String username);
}
