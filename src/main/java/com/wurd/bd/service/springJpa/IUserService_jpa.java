package com.wurd.bd.service.springJpa;

import com.wurd.bd.entity.User;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

public interface IUserService_jpa {

    Iterator<User> findAll() throws Exception;

    User findUserWithId(int id) throws Exception;

    List<User> findUsersWithNameAge(String name, int age) throws Exception;

    User save(User user) throws Exception;

    Page<User> findByPage(int pageNum, int pageSize) throws Exception;
}
