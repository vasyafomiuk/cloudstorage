package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Insert("INSERT INTO credentials (url, username, key, password, user_id)" +
            "VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    @Options(useGeneratedKeys = true, keyColumn = "credential_id")
    int save(Credential credential);

    @Update("UPDATE credentials " +
            "SET url = #{url}, " +
                "username = #{username}, " +
                "key = #{key}, " +
                "password = #{password}" +
            "WHERE credential_id = #{credentialId}")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    void update(Credential credential);

    @Select("SELECT * FROM credentials WHERE credential_id = #{credentialId}")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    Credential findByCredentialId(int credentialId);

    @Select("SELECT * FROM credentials WHERE user_id = #{userId}")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    List<Credential> findAll(int userId);

    @Select("SELECT * FROM credentials WHERE username = #{username}")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    List<Credential> findAllByUsername(String username);

    @Select("SELECT * FROM credentials WHERE credential_id = #{credentialId} ")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    Credential findCredentialByCredentialId(int credentialId);

    @Delete("DELETE FROM credentials WHERE credential_id = #{credentialId}")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    int delete(int credentialId);
}
