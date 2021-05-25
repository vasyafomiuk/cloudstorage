package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.forms.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialsService {
    private final CredentialsMapper credentialsMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialsMapper credentialsMapper, EncryptionService encryptionService) {
        this.credentialsMapper = credentialsMapper;
        this.encryptionService = encryptionService;
    }

    public List<CredentialsForm> getCredentials(int userId) {
        List<Credential> credentialList = credentialsMapper.findAll(userId);
        List<CredentialsForm> credentialsFormList = new ArrayList<>();
        credentialList.forEach(credential -> {
            String decryptedPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
            CredentialsForm credentialsForm = new CredentialsForm();
            credentialsForm.setCredentialId(credential.getCredentialId());
            credentialsForm.setKey(credential.getKey());
            credentialsForm.setPassword(credential.getPassword());
            credentialsForm.setUrl(credential.getUrl());
            credentialsForm.setDecryptedPassword(decryptedPassword);
            credentialsForm.setUsername(credential.getUsername());
            credentialsFormList.add(credentialsForm);
        });
        return credentialsFormList;
    }

    public int insertCredentials(Credential credential) {
        return credentialsMapper.save(credential);
    }

    public void updateCredentials(Credential credential) {
        credentialsMapper.update(credential);
    }

    public void delete(int credentialId) {
        Credential credential = credentialsMapper.findCredentialByCredentialId(credentialId);
        credentialsMapper.delete(credential.getCredentialId());
    }

    public List<Credential> getCredentials(String username) {
        return credentialsMapper.findAllByUsername(username);
    }
}
