package com.example.demo.controller;

import com.example.demo.entity.AUser;
import com.example.demo.entity.Privilege;
import com.example.demo.entity.Role;
import com.example.demo.model.RoleModel;
import com.example.demo.model.UserModel;
import com.example.demo.service.PrivilegeService;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PrivilegeService privilegeService;

    @PostMapping("/user/create")
    public AUser newUser(@RequestBody UserModel userModel){
        return userService.register(userModel);

    }

    @GetMapping("/user/read/total")
    public int getAllUsersNumber(){
        return userService.getAllUsers().size();
    }

    @PutMapping("/user/update/{username}")
    public void getPage(@PathVariable String username, @RequestBody UserModel userModel){
        //log.info(productService.getAllProducts().toString());
        userService.updateUserWithUsername(username, userModel);
    }

    @GetMapping("/user/read/Page")
    public List<AUser> getPage(@RequestParam("page") int page, @RequestParam("size") int size){
        //log.info(productService.getAllProducts().toString());
        return userService.getUsersPage(page, size);
    }

    @GetMapping("/roles/read/all")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    @GetMapping("/roles/read/{id}")
    public Role getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }

    @PostMapping("/roles/create")
    public void createRole(@RequestBody RoleModel roleModel){
        roleService.createRole(roleModel);
    }

    @PutMapping("/roles/update/{id}")
    public void updateRole(@RequestBody RoleModel roleModel, @PathVariable Long id) {
        roleService.updateRole(roleModel, id);

    }

    @GetMapping("/privileges/read/all")
    public List<Privilege> getAllPrivileges(){
        return privilegeService.getAll();
    }


}
