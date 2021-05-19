package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes")
@ToString
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Integer noteId;
    @Column(name = "note_title")
    private String noteTitle;
    @Column(name = "note_description")
    private String noteDescription;
    @Column(name = "user_id")
    private int userId;
}
