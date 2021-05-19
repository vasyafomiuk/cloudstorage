package com.udacity.jwdnd.course1.cloudstorage.forms;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NoteForm {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
}
