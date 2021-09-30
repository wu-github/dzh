package com.wurd.bd.controller;

import com.wurd.bd.entity.User;
import com.wurd.bd.exception.CommonException;
import com.wurd.bd.service.Mybatis.IUserService_m;
import com.wurd.bd.service.springData.jdcb.IUserService_s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService_m userService_m;

    @Autowired
    private IUserService_s userService_s;

    @GetMapping("/_m/id")
    public User test_m_id(int id) {
        User test = userService_m.getTest(id);
        return test;
    }

    @GetMapping("/_s/all")
    public Iterator<User> test_s_all() {
        Iterator<User> tests = userService_s.findAll();
        return tests;
    }

    @GetMapping("/_s/id")
    public User test_s_id(int id, String name) {
        User test = userService_s.findTestWithId(id);
        return test;
    }

    @GetMapping("/_s/search")
    public List<User> test_s_search(String name, int age) {
        List<User> tests = userService_s.findTestsWithNameAge(name, age);
        return tests;
    }
}
