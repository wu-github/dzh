package com.wurd.bd.service.springJpa.impl;

import com.wurd.bd.entity.User;
import com.wurd.bd.jpa.UserRepository_jpa;
import com.wurd.bd.service.springJpa.IUserService_jpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserService_jpa implements IUserService_jpa {

    @Autowired
    private UserRepository_jpa userRepository_jpa;

    @Override
    public Iterator<User> findAll() {

        return userRepository_jpa.findAll().iterator();
    }

    @Override
    public User findUserWithId(int id) {

        return userRepository_jpa.findUserWithId(id);
    }

    @Override
    public List<User> findUsersWithNameAge(String name, int age) {

        return userRepository_jpa.findUsersWithNameAge(name, age);
    }

    @Override
    public User save(User user) {
        return userRepository_jpa.save(user);
    }
}
