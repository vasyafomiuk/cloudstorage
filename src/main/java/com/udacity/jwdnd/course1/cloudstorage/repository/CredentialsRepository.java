package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CredentialsRepository extends CrudRepository<Credential, Long> {
    Credential save(Credential credential);

    Credential findByCredentialId(int credentialId);

    List<Credential> findAll();

    List<Credential> findAllByUsername(String username);

    Credential findCredentialByCredentialId(int credentialId);

    void delete(Credential credential);
}
