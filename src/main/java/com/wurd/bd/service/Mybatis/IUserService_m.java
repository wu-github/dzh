package com.wurd.bd.service.Mybatis;

import com.wurd.bd.entity.User;

import java.util.List;

public interface IUserService_m {

    User getUser(int id) throws Exception;

    List<User> getAllUser() throws Exception;
}
