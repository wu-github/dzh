package com.wurd.bd.service.Mybatis.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wurd.bd.entity.User;
import com.wurd.bd.database.mybatis.mapper.UserRepository_m;
import com.wurd.bd.service.Mybatis.IUserService_m;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public PageInfo<User> getUserPage(int pageNum, int pageSize) throws Exception {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userRepository_m.all();
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        return pageInfo;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void update(List<User> users) throws Exception{
        for(User user : users){
            int r = userRepository_m.update(user);
        }
    }

}
