package com.wurd.bd.controller;

import com.github.pagehelper.PageInfo;
import com.wurd.bd.constants.Constants;
import com.wurd.bd.entity.User;
import com.wurd.bd.exception.CommonException;
import com.wurd.bd.i18n.MessageUtil;
import com.wurd.bd.service.Mybatis.IUserService_m;
import com.wurd.bd.service.springData.jdcb.IUserService_s;
import com.wurd.bd.service.springJpa.IUserService_jpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService_m userService_m;

    @Autowired
    private IUserService_s userService_s;

    @Autowired
    private IUserService_jpa userService_jpa;

    @Autowired
    private MessageUtil messageUtil;

    @GetMapping("/_m/id")
    public User user_m_id(int id) throws Exception {
        try {
            User test = userService_m.getUser(id);
            return test;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @GetMapping("/_m/all")
    public List<User> user_m_all() throws Exception {
        try {
            List<User> test = userService_m.getAllUser();
            return test;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @GetMapping("/_m/page")
    public PageInfo<User> user_m_page(int pageNum, int pageSize) throws Exception {
        try {
            PageInfo<User> test = userService_m.getUserPage(pageNum, pageSize);
            return test;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @PostMapping("/_m/update")
    public List<User> user_m_update(@RequestBody @Valid List<User> users) throws Exception {
        try {
            userService_m.update(users);
            return users;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_UPDATE));
        }
    }

    @GetMapping("/_jpa/all")
    public Iterator<User> user_jpa_all() throws Exception {
        try {
            Iterator<User> tests = userService_jpa.findAll();
            return tests;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @GetMapping("/_jpa/page")
    public Page<User> user_jpa_page(int pageNum, int pageSize) throws Exception {
        try {
            Page<User> tests = userService_jpa.findByPage(pageNum, pageSize);
            return tests;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @GetMapping("/_jpa/id")
    public User user_jpa_id(int id) throws Exception {
        try {
            User test = userService_jpa.findUserWithId(id);
            return test;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @PostMapping("/_jpa/save")
    public User user_jpa_save(@RequestBody @Valid User user) throws Exception {
        try {
            User u = userService_jpa.save(user);
            return u;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_SAVE));
        }
    }

    @GetMapping("/_jpa/search")
    public List<User> user_jpa_search(String name, int age) throws Exception {
        try {
            List<User> tests = userService_jpa.findUsersWithNameAge(name, age);
            return tests;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @GetMapping("/_s/all")
    @Deprecated
    public Iterator<User> user_s_all() throws Exception {
        try {
            Iterator<User> tests = userService_s.findAll();
            return tests;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @GetMapping("/_s/id")
    @Deprecated
    public User user_s_id(int id) throws Exception {
        try {
            User test = userService_s.findUserWithId(id);
            return test;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

    @GetMapping("/_s/search")
    @Deprecated
    public List<User> user_s_search(String name, int age) throws Exception {
        try {
            List<User> tests = userService_s.findUsersWithNameAge(name, age);
            return tests;
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CommonException(messageUtil.getMessage(Constants.ERROR_USER_GET));
        }
    }

}
