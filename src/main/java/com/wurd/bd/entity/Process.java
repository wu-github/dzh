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
    @Column("p_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @javax.persistence.Id
    @javax.persistence.Column(name = "p_id", nullable = false)
    private int p_id;
    @javax.persistence.Column(name = "p_name", nullable = false)
    private String p_name;
}
