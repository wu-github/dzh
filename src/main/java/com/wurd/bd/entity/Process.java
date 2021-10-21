package com.wurd.bd.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import javax.persistence.*;

@Data
@Table(name = "process")
@Entity
public class Process {
    @Id
    @Column("id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "id", nullable = false)
    private int id;
    @javax.persistence.Column(name = "name", nullable = false)
    private String name;
}
