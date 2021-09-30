package com.wurd.bd.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {

    @Id
    private int id;
    private String name;
    private int age;

}