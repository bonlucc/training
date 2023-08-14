package com.example.demo.repository;

import com.example.demo.entity.AUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AUser, Long> {
    Optional<AUser> findByUsername(String username);

    Optional<AUser> findByEmail(String email);
}
