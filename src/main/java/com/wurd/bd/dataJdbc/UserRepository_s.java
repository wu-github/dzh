package com.wurd.bd.dataJdbc;

import com.wurd.bd.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository_s extends CrudRepository<User, Integer> {

    @Query("select * from user where id = :id")
    public User findUserWithId(@Param("id") int id);

    @Query("select * from user where name = :name or age = :age")
    public List<User> findUsersWithNameAge(@Param("name") String name, @Param("age") int age);

}
