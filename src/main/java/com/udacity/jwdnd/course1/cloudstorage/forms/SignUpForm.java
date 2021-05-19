package com.udacity.jwdnd.course1.cloudstorage.forms;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignUpForm {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
