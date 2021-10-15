package com.wurd.bd.service.springJpa;

import com.wurd.bd.entity.User;

import java.util.Iterator;
import java.util.List;

public interface IUserService_jpa {

    Iterator<User> findAll();

    User findUserWithId(int id);

    List<User> findUsersWithNameAge(String name, int age);

    User save(User user);
}
