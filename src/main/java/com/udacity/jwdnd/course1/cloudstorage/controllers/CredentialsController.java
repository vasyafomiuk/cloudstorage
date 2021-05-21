package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.forms.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CredentialsController {
    private final Logger logger = LoggerFactory.getLogger(CredentialsController.class);
    private final CredentialsService credentialsService;
    private final EncryptionService encryptionService;

    public CredentialsController(CredentialsService credentialsService, EncryptionService encryptionService) {
        this.credentialsService = credentialsService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/credentials")
    public String postCredentials(@ModelAttribute("credentialsForm") @Valid CredentialsForm credentialsForm,
                                  Model model,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirAttrs,
                                  @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            logger.error("There was a error " + bindingResult);
            redirAttrs.addFlashAttribute("error", "Invalid credentials");
            return "redirect:/home";
        }
        model.addAttribute("credentialsForm", new CredentialsForm());
        if (credentialsForm.getUsername() == null) {
            return "redirect:/home";
        }
        Credential credential = new Credential();
        String key = encryptionService.getRandomString();
        String encryptedPassword = encryptionService.encryptValue(credentialsForm.getPassword(), key);
        credential.setKey(key);
        credential.setPassword(encryptedPassword);
        credential.setUsername(credentialsForm.getUsername());
        credential.setUrl(credentialsForm.getUrl());
        credential.setUserId(user.getUserId());
        if (credentialsForm.getCredentialId() == null) {
            credentialsService.insertCredentials(credential);
        } else {
            credential.setCredentialId(credentialsForm.getCredentialId());
            credentialsService.updateCredentials(credential);
        }
        redirAttrs.addFlashAttribute("success", "Credentials created! Use this key to decrypt password: " + key);
        return "redirect:/home";
    }

    @GetMapping("/credentials/{credentialId}/delete")
    public String deleteCredentials(@PathVariable int credentialId) {
        credentialsService.delete(credentialId);
        return "redirect:/home";
    }
}
