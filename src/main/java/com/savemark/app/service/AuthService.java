package com.savemark.app.service;

import com.savemark.app.dto.LoginRequest;
import com.savemark.app.dto.RegistrationRequest;
import com.savemark.app.models.User;
import com.savemark.app.repositories.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepo userRepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository=new HttpSessionSecurityContextRepository();
    public AuthService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<Map<String, String>> registerUser(RegistrationRequest request)
    {
        User user=new User();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        userRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","User registered"));
    }
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request, HttpServletRequest httpRequest,
                                       HttpServletResponse httpResponse)
    {
        try
        {
            System.out.println(request);
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

//            create security context
            SecurityContext context= SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);

//            set authentication in current request
            SecurityContextHolder.setContext(context);
//            save securitycontext into http session
            securityContextRepository.saveContext(context,httpRequest,httpResponse);

//           return of response
            return ResponseEntity.ok(
                    Map.of(
                            "message","Login successful",
                            "username",authentication.getName()
                    )
            );
        }
        catch(BadCredentialsException e)
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message","Invalid username or password"));
        }
    }
}
