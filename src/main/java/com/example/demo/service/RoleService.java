package com.example.demo.service;

import com.example.demo.entity.Privilege;
import com.example.demo.entity.Role;
import com.example.demo.model.RoleModel;
import com.example.demo.repository.PrivilegeRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository, PrivilegeRepository privilegeRepository){
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }


    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void createRole(RoleModel roleModel) {
        List<Privilege> privileges = new ArrayList<>();
        for(String role : roleModel.getPrivileges()){
            privileges.add(privilegeRepository.findByName(role));
        }
        Role role = Role.builder()
                .name(roleModel.getName())
                .privileges(privileges)
                .build();
        roleRepository.save(role);
    }
}
