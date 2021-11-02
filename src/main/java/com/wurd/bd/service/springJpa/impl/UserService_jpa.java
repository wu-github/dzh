package com.wurd.bd.service.springJpa.impl;

import com.wurd.bd.database.jpa.UserRepository_jpa;
import com.wurd.bd.entity.User;
import com.wurd.bd.service.springJpa.IUserService_jpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class UserService_jpa implements IUserService_jpa {

    @Autowired
    private UserRepository_jpa userRepository_jpa;

    @Override
    public Iterator<User> findAll() throws Exception {

        return userRepository_jpa.findAll().iterator();
    }

    @Override
    public User findUserWithId(int id) throws Exception {

        return userRepository_jpa.findUserWithId(id);
    }

    @Override
    public List<User> findUsersWithNameAge(String name, int age) throws Exception {

        return userRepository_jpa.findUsersWithNameAge(name, age);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public User save(User user) throws Exception {
        return userRepository_jpa.save(user);
    }

    @Override
    public Page<User> findByPage(int pageNum, int pageSize) throws Exception {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return userRepository_jpa.findAll(pageable);
    }
}
