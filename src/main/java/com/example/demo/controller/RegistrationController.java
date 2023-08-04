package com.example.demo.controller;

import com.example.demo.entity.AUser;
import com.example.demo.entity.Product;
import com.example.demo.entity.Role;
import com.example.demo.model.RoleModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user")
    public AUser newUser(@RequestBody UserModel userModel){
        return userService.register(userModel);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/management/total")
    public int getAllUsersNumber(){
        return userService.getAllUsers().size();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/management/update/{username}")
    public void getPage(@PathVariable String username, @RequestBody UserModel userModel){
        //log.info(productService.getAllProducts().toString());
        userService.updateUserWithUsername(username, userModel);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user/management/Page")
    public List<AUser> getPage(@RequestParam("page") int page, @RequestParam("size") int size){
        //log.info(productService.getAllProducts().toString());
        return userService.getUsersPage(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/roles/all")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/roles/create")
    public void createRole(@RequestBody RoleModel roleModel){
        roleService.createRole(roleModel);
    }

}
