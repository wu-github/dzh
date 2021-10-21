package com.wurd.bd.controller;

import com.wurd.bd.entity.User;
import com.wurd.bd.service.Mybatis.IUserService_m;
import com.wurd.bd.service.springData.jdcb.IUserService_s;
import com.wurd.bd.service.springJpa.IUserService_jpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService_m userService_m;

    @Autowired
    private IUserService_s userService_s;

    @Autowired
    private IUserService_jpa userService_jpa;

    @GetMapping("/_m/id")
    public User user_m_id(int id) {
        User test = userService_m.getUser(id);
        return test;
    }

    @GetMapping("/_s/all")
    public Iterator<User> user_s_all() {
        Iterator<User> tests = userService_s.findAll();
        return tests;
    }

    @GetMapping("/_s/id")
    public User user_s_id(int id) {
        User test = userService_s.findUserWithId(id);
        return test;
    }

    @GetMapping("/_s/search")
    public List<User> user_s_search(String name, int age) {
        List<User> tests = userService_s.findUsersWithNameAge(name, age);
        return tests;
    }

    @GetMapping("/_jpa/all")
    public Iterator<User> user_jpa_all() {
        Iterator<User> tests = userService_jpa.findAll();
        return tests;
    }

    @GetMapping("/_jpa/id")
    public User user_jpa_id(int id) {
        User test = userService_jpa.findUserWithId(id);
        return test;
    }

    @PostMapping("/_jpa/save")
    public User user_jpa_save(@RequestBody User user) {
        User test = userService_jpa.save(user);
        return test;
    }

    @GetMapping("/_jpa/search")
    public List<User> user_jpa_search(String name, int age) {
        List<User> tests = userService_jpa.findUsersWithNameAge(name, age);
        return tests;
    }

}
