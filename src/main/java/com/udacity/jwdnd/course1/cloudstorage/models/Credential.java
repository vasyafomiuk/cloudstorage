package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credentials")
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "credential_id")
    private Integer credentialId;
    private String username;
    private String key;
    private String password;
    @Column(name = "user_id")
    private int userId;
    private String url;
}
