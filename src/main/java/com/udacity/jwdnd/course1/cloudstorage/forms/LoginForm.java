package com.udacity.jwdnd.course1.cloudstorage.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    private String username;
    private String password;
    private boolean loggedOut;
    private boolean loginError;
}
