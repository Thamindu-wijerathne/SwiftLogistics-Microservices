package com.cms.demo.controller;

import com.cms.demo.model.User;
import com.cms.demo.service.JwtService;
import com.cms.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public User createUser(@RequestBody User user) {
        log.info("Received user Create: {}", user);
        return userService.addUser(user);
    }

    @PostMapping("/hello")
    public String hello(){
        return "Hello";
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User userRequest) {
        System.out.println(userRequest.getEmail());
        log.info("Received user Login: {}", userRequest);
        User user = userService.loginUser(userRequest);

        if (user != null) {

            String jwtToken = jwtService.generateToken(user.getEmail(), user.getRole());

            Map<String, String> response = new HashMap<>();
            response.put("jwtToken", jwtToken);
            response.put("message", "Login successful");
            response.put("role", user.getRole());
            response.put("email", user.getEmail()); // or getUsername() depending on your entity

            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
}
