package com.example.demo.repository;

import com.example.demo.entity.AUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AUser, Long> {
    AUser findByUsername(String username);
}
