package com.example.demo.security;

import com.example.demo.entity.AUser;
import com.example.demo.entity.Privilege;
import com.example.demo.entity.Role;
import com.example.demo.repository.PrivilegeRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@Transactional
@Slf4j

public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProductService productService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        productService.initialize();

        Privilege readPrivilege
                = createPrivilegeIfNotFound("appProd_READ");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("appProd_WRITE");
        Privilege deletePrivilege
                = createPrivilegeIfNotFound("appProd_DELETE");

        List<Privilege> adminPrivileges = new ArrayList<>();
        adminPrivileges.add(readPrivilege);
        adminPrivileges.add(writePrivilege);
        adminPrivileges.add(deletePrivilege);

        Privilege setupPrivilege;
        List<String> primitivePrivileges = Arrays.asList(
                "appUser_WRITE",
                "appUser_READ",
                "appUser_DELETE",
                "appRoles_WRITE",
                "appRoles_READ",
                "appRoles_DELETE",
                "appPrivileges_WRITE",
                "appPrivileges_READ",
                "appPrivileges_DELETE");

        for(String privilege : primitivePrivileges){
            setupPrivilege = createPrivilegeIfNotFound(privilege);
            //log.info(String.valueOf(setupPrivilege));
            adminPrivileges.add(setupPrivilege);
        }

        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_STAFF", Arrays.asList(writePrivilege, readPrivilege));
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role staffRole = roleRepository.findByName("ROLE_STAFF");
        log.info(roleRepository.findByName("ROLE_USER").toString());
        Role userRole = roleRepository.findByName("ROLE_USER");


        AUser admin = new AUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@admin.com");
        admin.setRoles(Arrays.asList(adminRole, staffRole));
        admin.setEnabled(true);
        userRepository.save(admin);

        AUser staff = new AUser();
        staff.setUsername("staff");
        staff.setPassword(passwordEncoder.encode("staff"));
        staff.setEmail("staff@staff.com");
        staff.setRoles(Arrays.asList(staffRole));
        staff.setEnabled(true);
        userRepository.save(staff);

        AUser user = new AUser();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setEmail("user@user.com");
        user.setRoles(Arrays.asList(userRole));
        user.setEnabled(true);
        userRepository.save(user);

        alreadySetup = true;
    }

    //@Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = Privilege.builder().name(name).build();
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    //@Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = Role.builder().name(name).build();
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}
