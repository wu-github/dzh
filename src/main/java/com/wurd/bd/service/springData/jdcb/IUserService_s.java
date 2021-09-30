package com.wurd.bd.service.springData.jdcb;

import com.wurd.bd.entity.User;

import java.util.Iterator;
import java.util.List;

public interface IUserService_s {

    Iterator<User> findAll();

    User findTestWithId(int id);

    List<User> findTestsWithNameAge(String name, int age);
}
