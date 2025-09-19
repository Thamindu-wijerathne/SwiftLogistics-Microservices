package com.cms.demo.service;


import com.cms.demo.model.User;
import com.cms.demo.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(JwtService jwtService, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(User user) {
        User requestedUser = userRepository.findByEmail(user.getEmail());

        if (requestedUser != null && requestedUser.getPassword().equals(user.getPassword())) {
            return requestedUser;
        } else {
            return null;
        }
    }

}
