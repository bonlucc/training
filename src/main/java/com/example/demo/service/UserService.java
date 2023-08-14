package com.example.demo.service;

import com.example.demo.entity.AUser;
import com.example.demo.entity.Role;
import com.example.demo.error.ResourceAlreadyExistsException;
import com.example.demo.error.ResourceNotFoundException;
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
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;
    public AUser register(@NotNull UserModel userModel) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        Collection<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole;
        if(userModel.getRoles().isEmpty()) {
            optionalRole = roleRepository.findByName("ROLE_USER");
            roles.add(optionalRole.get());
        }
        else{
            for (String role : userModel.getRoles()) {
                optionalRole = roleRepository.findByName(role);
                if(optionalRole.isEmpty()) throw new ResourceNotFoundException("No role " + role + "found");
                roles.add(optionalRole.get());
            }
        }
        String username = userModel.getUsername(), email = userModel.getEmail();
        if(userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent())
            throw new ResourceAlreadyExistsException("Username or Email already signed up");


        AUser user = AUser.builder()
                .username(username)
                .password(passwordEncoder.encode(userModel.getPassword()))
                .email(email)
                .roles(roles)
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    public List<AUser> getAllUsers(){
        return userRepository.findAll();
    }

    public List<AUser> getUsersPage(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public void updateUserWithUsername(String username, UserModel userModel) throws ResourceNotFoundException {
        Optional<Role> optionalRole;
        Optional<AUser> optionalAUser = userRepository.findByUsername(username);
        if(optionalAUser.isEmpty()) throw new ResourceNotFoundException("This user is not signed up");
        AUser user = optionalAUser.get();
        String email = userModel.getEmail();
        if(!email.isEmpty()) user.setEmail(email);
        String password = userModel.getPassword();
        if(!password.isEmpty()) user.setPassword(passwordEncoder.encode(password));
        Collection<Role> roles = new ArrayList<>();
        for (String role : userModel.getRoles()) {
            optionalRole = roleRepository.findByName(role);
            if(optionalRole.isEmpty()) throw new ResourceNotFoundException("No role " + role + "found");
            roles.add(optionalRole.get());
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
