package com.wurd.bd.mybatis.mapper;

import com.wurd.bd.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRepository_m {

    User select(int id);
}

