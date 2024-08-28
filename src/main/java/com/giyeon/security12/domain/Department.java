package com.giyeon.security12.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;

    private String name;


}
