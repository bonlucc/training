package com.example.demo.service;

import com.example.demo.entity.Privilege;
import com.example.demo.entity.Role;
import com.example.demo.error.ResourceAlreadyExistsException;
import com.example.demo.error.ResourceNotFoundException;
import com.example.demo.model.RoleModel;
import com.example.demo.repository.PrivilegeRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;

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

    public void createRole(RoleModel roleModel) throws ResourceAlreadyExistsException {
        List<Privilege> privileges = new ArrayList<>();
        for(String privilege : roleModel.getPrivileges()){
            privileges.add(privilegeRepository.findByName(privilege));
        }
        String roleName = "ROLE_" + roleModel.getName().toUpperCase();
        if(roleRepository.findByName(roleName).isPresent()) throw new ResourceAlreadyExistsException("Role already present");

        Role role = Role.builder()
                .name(roleName)
                .privileges(privileges)
                .build();
        roleRepository.save(role);
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }

    public void updateRole(RoleModel roleModel, Long id) {
        List<Privilege> privileges = new ArrayList<>();
        for(String privilege : roleModel.getPrivileges()){
            privileges.add(privilegeRepository.findByName(privilege));
        }
        Role role = roleRepository.findById(id).orElseThrow();
        role.setPrivileges(privileges);
        roleRepository.save(role);
    }

    public Role getRoleByName(String name) throws ResourceNotFoundException {
        Optional<Role> optionalRole = roleRepository.findByName(name);
        if(optionalRole.isPresent()) return optionalRole.get();
        else throw new ResourceNotFoundException("No such Role found");
    }

    public void deleteRoleById(Long id){
        roleRepository.deleteById(id);
    }
}
