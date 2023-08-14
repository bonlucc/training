package com.example.demo.controller;

import com.example.demo.entity.AUser;
import com.example.demo.entity.Privilege;
import com.example.demo.entity.Role;
import com.example.demo.error.ResourceAlreadyExistsException;
import com.example.demo.error.ResourceNotFoundException;
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
    public AUser newUser(@RequestBody UserModel userModel) throws ResourceNotFoundException, ResourceAlreadyExistsException {
        return userService.register(userModel);
    }

    @GetMapping("/user/read/total")
    public int getAllUsersNumber(){
        return userService.getAllUsers().size();
    }

    @PutMapping("/user/update/{username}")
    public void getPage(@PathVariable String username, @RequestBody UserModel userModel) throws ResourceNotFoundException {
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

    @GetMapping("/roles/read/{name}")
    public Role getRoleByName(@PathVariable String name) throws ResourceNotFoundException {
        return roleService.getRoleByName(name);
    }

    @PostMapping("/roles/create")
    public void createRole(@RequestBody RoleModel roleModel) throws ResourceAlreadyExistsException {
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

    @DeleteMapping("/user/delete/{id}")
    public void deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @DeleteMapping("/roles/delete/{id}")
    public void deleteRoleById(@PathVariable Long id){
        roleService.deleteRoleById(id);
    }

}
