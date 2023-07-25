package com.example.demo.controller;

import com.example.demo.entity.AUser;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class RegistrationController {
    @Autowired
    private UserService userService;
    @PostMapping("/user")
    public AUser newUser(@RequestBody UserModel userModel){
        return userService.register(userModel);

    }


}
