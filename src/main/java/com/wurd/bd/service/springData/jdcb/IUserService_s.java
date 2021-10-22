package com.wurd.bd.service.springData.jdcb;

import com.wurd.bd.entity.User;

import java.util.Iterator;
import java.util.List;

/**
 * use spring jpa instead
 */
public interface IUserService_s {

    Iterator<User> findAll() throws Exception;

    User findUserWithId(int id) throws Exception;

    List<User> findUsersWithNameAge(String name, int age) throws Exception;
}
