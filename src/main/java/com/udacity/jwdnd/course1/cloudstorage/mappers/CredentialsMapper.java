package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Insert("INSERT INTO credentials (url, username, key, password, user_id)" +
            "VALUES ('#{url}', '#{username}', '#{key}', '#{password}', '#{userId}')")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    Credential save(Credential credential);

    @Select("SELECT * FROM credentials WHERE credential_id = #{credentialId}")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    Credential findByCredentialId(int credentialId);

    @Select("SELECT * FROM credentials")
    @Results(value = {
            @Result(column = "credential_id", property = "credentialId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    List<Credential> findAll();

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
