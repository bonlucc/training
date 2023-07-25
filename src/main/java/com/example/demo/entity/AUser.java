package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AUser implements Serializable {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY

    )
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role = "USER";
}
