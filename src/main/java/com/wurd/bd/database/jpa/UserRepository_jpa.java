package com.wurd.bd.database.jpa;

import com.wurd.bd.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository_jpa extends JpaRepository<User, String> {

    @Query(value = "select * from user where id = ?1", nativeQuery = true)
    public User findUserWithId(int id);

    @Query("from User where name = :name or age = :age")
    public List<User> findUsersWithNameAge(@Param("name") String name, @Param("age") int age);
}
