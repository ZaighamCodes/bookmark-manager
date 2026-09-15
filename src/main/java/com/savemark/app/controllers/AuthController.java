package com.savemark.app.controllers;

import com.savemark.app.dto.LoginRequest;
import com.savemark.app.dto.RegistrationRequest;
import com.savemark.app.models.User;
import com.savemark.app.service.UserService;
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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;
import java.util.Map;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository=new HttpSessionSecurityContextRepository();
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //    for registering our new user
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegistrationRequest request)
    {
        System.out.println(request);
        return userService.registerUser(request);
    }

//    for logging in
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request,
                                   HttpServletRequest httpRequest,
                                   HttpServletResponse httpResponse)
    {
        try
        {
//            lets authenticate our username and password
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
