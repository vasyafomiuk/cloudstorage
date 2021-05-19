package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.repository.CredentialsRepository;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialsService {
    private final CredentialsRepository credentialsRepository;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsRepository credentialsRepository, EncryptionService encryptionService) {
        this.credentialsRepository = credentialsRepository;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getCredentials() {
        List<Credential> credentialList = credentialsRepository.findAll();
        credentialList.forEach(credential -> credential.setPassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey())));
        return credentialList;
    }

    public Credential insertCredentials(Credential credential) {
        return credentialsRepository.save(credential);
    }

    public void delete(int credentialId) {
        Credential credential = credentialsRepository.findCredentialByCredentialId(credentialId);
        credentialsRepository.delete(credential);
    }

    public List<Credential> getCredentials(String username) {
        return credentialsRepository.findAllByUsername(username);
    }
}
