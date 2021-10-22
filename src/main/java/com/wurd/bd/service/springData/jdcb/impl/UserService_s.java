package com.wurd.bd.service.springData.jdcb.impl;

import com.wurd.bd.database.springData.UserRepository_s;
import com.wurd.bd.entity.User;
import com.wurd.bd.service.springData.jdcb.IUserService_s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserService_s implements IUserService_s {

    @Autowired(required = false)
    private UserRepository_s userRepository_s;

    @Override
    public Iterator<User> findAll() throws Exception {

        return userRepository_s.findAll().iterator();
    }

    @Override
    public User findUserWithId(int id) throws Exception {

        return userRepository_s.findUserWithId(id);
    }

    @Override
    public List<User> findUsersWithNameAge(String name, int age) throws Exception {

        return userRepository_s.findUsersWithNameAge(name, age);
    }
}
