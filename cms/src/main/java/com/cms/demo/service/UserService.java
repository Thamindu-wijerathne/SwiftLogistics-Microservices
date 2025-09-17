package com.cms.demo.service;


import com.cms.demo.model.User;
import com.cms.demo.repo.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User loginUser(User user) {
        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }

}
