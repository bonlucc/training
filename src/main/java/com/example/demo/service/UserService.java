package com.example.demo.service;

import com.example.demo.entity.AUser;
import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.model.UserModel;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    public AUser register(@NotNull UserModel userModel) {
        Collection<Role> roles = new ArrayList<>();
        if(userModel.getRoles().isEmpty()) {roles.add(roleRepository.findByName("ROLE_USER"));}
        else{
            for (String role : userModel.getRoles()) {
                roles.add(roleRepository.findByName(role));
            }
        }
        AUser user = AUser.builder()
                .username(userModel.getUsername())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .email(userModel.getEmail())
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public List<AUser> getAllUsers(){
        return userRepository.findAll();
    }

    public List<AUser> getUsersPage(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public void updateUserWithUsername(String username, UserModel userModel) {
        AUser user = userRepository.findByUsername(username);
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        Collection<Role> roles = new ArrayList<>();
        if(userModel.getRoles().isEmpty()) {roles.add(roleRepository.findByName("ROLE_USER"));}
        else{
            for (String role : userModel.getRoles()) {
                roles.add(roleRepository.findByName(role));
            }
        }
        user.setRoles(roles);



    }
}
