package com.wurd.bd.service.Mybatis;

import com.github.pagehelper.PageInfo;
import com.wurd.bd.entity.User;

import java.util.List;

public interface IUserService_m {

    User getUser(int id) throws Exception;

    List<User> getAllUser() throws Exception;

    PageInfo<User> getUserPage(int pageNum, int pageSize) throws Exception;

    void update(List<User> users) throws Exception;
}
