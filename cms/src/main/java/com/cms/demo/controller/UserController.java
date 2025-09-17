package com.cms.demo.controller;

import com.cms.demo.model.User;
import com.cms.demo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        log.info("Received user Create: {}", user);
        return userService.addUser(user);
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User userRequest) {
        log.info("Received user Login: {}", userRequest);
        User user = userService.loginUser(userRequest);

        if (user != null) {
            return ResponseEntity.ok(user); // 200 OK with user data
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
}
