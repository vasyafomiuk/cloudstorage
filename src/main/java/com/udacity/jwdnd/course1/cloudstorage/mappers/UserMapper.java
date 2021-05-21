package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username}")
    User findUserByUsername(String username);

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Insert("INSERT INTO user (username, salt, password, first_name, last_name) VALUES ('#{username}', '#{salt}, '#{password}, '#{firstName}, '#{lastName}')")
    User save(User user);

    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    void delete(int userId);

}
