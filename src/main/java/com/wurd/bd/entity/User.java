package com.wurd.bd.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Data
@Table(name = "user")
@Entity
public class User {
    @Id
    @Column("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    private int id;
    private String name;
    private int age;

}