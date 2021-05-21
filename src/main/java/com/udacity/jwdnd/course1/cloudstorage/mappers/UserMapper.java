package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username}")
    @Results(value = {
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName")
    })
    User findUserByUsername(String username);

    @Select("SELECT * FROM users")
    @Results(value = {
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "first_name", property = "firstName"),
            @Result(column = "last_name", property = "lastName")
    })
    List<User> findAll();

    @Insert("INSERT INTO users (username, salt, password, first_name, last_name) " +
            "VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyColumn = "user_id")
    int save(User user);

    @Delete("DELETE FROM users WHERE user_id = #{userId}")
    @Options(keyColumn = "user_id")
    @Results(value = {
            @Result(column = "user_id", property = "userId", id = true),
    })
    int delete(int userId);

}
