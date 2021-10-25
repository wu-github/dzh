package com.wurd.bd.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"tel"})})
@Entity
public class User {
    @Id
    @Column("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    private int id;
    @javax.persistence.Column(name = "name", nullable = false)
    @NotBlank
    private String name;
    private int age;
    @javax.persistence.Column(name = "tel", nullable = false, updatable = false)
    @NotBlank
    private String tel;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private List<Process> processes;

    private int active = 1;

}