package com.wurd.bd.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"u_tel"})})
@Entity
public class User {
    @Id
    @Column("u_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "u_id", nullable = false)
    private int u_id;
    @javax.persistence.Column(name = "u_name", nullable = false)
    @NotBlank
    private String u_name;
    private int u_age;
    @javax.persistence.Column(name = "u_tel", nullable = false, updatable = false)
    @NotBlank
    private String u_tel;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private List<Process> processes;

    private int active = 1;

}