package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.*;
import org.apache.ibatis.annotations.Mapper;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int userId;
    private String username;
    private String salt;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
}
