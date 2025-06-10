package com.example.shopping_store.service;

import com.example.shopping_store.model.CustomUser;
import com.example.shopping_store.model.Roles;
import com.example.shopping_store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveUser(CustomUser user) {
        System.out.println(user);
        if (user.getUsername().trim().isEmpty() || user.getPassword().trim().isEmpty() || user.getFirstName().trim().isEmpty() || user.getLastName().trim().isEmpty() || user.getEmail().trim().isEmpty()) {
         return "Fields cannot be empty";
        }
        if (userRepository.getUserByUsername(user.getUsername()) != null) {
            return "User already exists";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Encoded password: " + user.getPassword()); // Log the encoded password

        if (user.getRole() == null) {
            user.setRole(Roles.USER);
        }
        return userRepository.saveUser(user);
    }
    public CustomUser updateUser(CustomUser user) {
        if (user.getUsername().trim().isEmpty() || user.getPassword().trim().isEmpty() || user.getFirstName().trim().isEmpty() || user.getLastName().trim().isEmpty() || user.getEmail().trim().isEmpty()) {
            return null;
        }
        if (userRepository.getUserByUsername(user.getUsername()) == null) {
            return null;
        }
        return userRepository.updateUser(user);
    }
    public String deleteUser(String username) {
        if (userRepository.getUserByUsername(username) == null) {
            return "User does not exist";
        }
        return userRepository.deleteUser(username);
    }
    public CustomUser getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }


    public CustomUser getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}
