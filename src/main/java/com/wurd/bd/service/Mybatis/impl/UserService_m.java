package com.wurd.bd.service.Mybatis.impl;

import com.wurd.bd.entity.User;
import com.wurd.bd.database.mybatis.mapper.UserRepository_m;
import com.wurd.bd.service.Mybatis.IUserService_m;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService_m implements IUserService_m {

    @Autowired
    private UserRepository_m userRepository_m;

    @Override
    public User getUser(int id) throws Exception {
        return userRepository_m.select(id);
    }

    @Override
    public List<User> getAllUser() throws Exception {
        return userRepository_m.all();
    }
}
