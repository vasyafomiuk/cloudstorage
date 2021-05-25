package com.udacity.jwdnd.course1.cloudstorage.forms;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CredentialsForm {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
    private int userId;
    private String key;
    private String decryptedPassword;
}
