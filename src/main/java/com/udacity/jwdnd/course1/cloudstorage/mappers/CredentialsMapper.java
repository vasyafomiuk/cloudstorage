package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CredentialsMapper extends CrudRepository<Credential, Long> {
    Credential save(Credential credential);

    Credential findByCredentialId(int credentialId);

    List<Credential> findAll();

    List<Credential> findAllByUsername(String username);

    Credential findCredentialByCredentialId(int credentialId);

    void delete(Credential credential);
}
