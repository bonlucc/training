package com.example.demo.service;

import com.example.demo.entity.AUser;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public AUser register(@NotNull UserModel userModel) {
        AUser user = AUser.builder()
                .username(userModel.getUsername())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .email(userModel.getEmail())
                .role("USER")
                .build();

        return userRepository.save(user);
    }
}
