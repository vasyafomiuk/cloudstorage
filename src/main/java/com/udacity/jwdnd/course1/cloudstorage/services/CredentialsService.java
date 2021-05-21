package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials() {
        List<Credential> credentialList = credentialsMapper.findAll();
        credentialList.forEach(credential -> credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey())));
        return credentialList;
    }

    public Credential insertCredentials(Credential credential) {
        return credentialsMapper.save(credential);
    }

    public void delete(int credentialId) {
        Credential credential = credentialsMapper.findCredentialByCredentialId(credentialId);
        credentialsMapper.delete(credential);
    }

    public List<Credential> getCredentials(String username) {
        return credentialsMapper.findAllByUsername(username);
    }
}
