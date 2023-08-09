package com.example.demo.service;

import com.example.demo.entity.Privilege;
import com.example.demo.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
    @Autowired
    private PrivilegeRepository privilegeRepository;

    public List<Privilege> getAll(){
        return privilegeRepository.findAll();
    }
}
