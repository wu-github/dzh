package com.wurd.bd.database.mybatis.mapper;

import com.wurd.bd.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserRepository_m {

    User select(int id);

    List<User> all();
}

