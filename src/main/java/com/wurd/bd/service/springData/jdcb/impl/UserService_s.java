package com.wurd.bd.service.springData.jdcb.impl;

import com.wurd.bd.dataJdbc.UserRepository_s;
import com.wurd.bd.entity.User;
import com.wurd.bd.service.springData.jdcb.IUserService_s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserService_s implements IUserService_s {

    @Autowired
    private UserRepository_s userRepository_s;

    @Override
    public Iterator<User> findAll() {

        return userRepository_s.findAll().iterator();
    }

    @Override
    public User findTestWithId(int id) {

        return userRepository_s.findUserWithId(id);
    }

    @Override
    public List<User> findTestsWithNameAge(String name, int age) {

        return userRepository_s.findUsersWithNameAge(name, age);
    }
}
