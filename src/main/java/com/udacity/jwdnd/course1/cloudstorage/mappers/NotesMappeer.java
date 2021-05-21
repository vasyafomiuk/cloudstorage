package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotesMappeer extends CrudRepository<Note, Long> {

    Note save(Note note);

    List<Note> findALlByOrderByNoteIdAsc();

    List<Note> findALlByNoteTitleOrderByNoteIdAsc(String noteTitle);


    Note findNoteByNoteId(int noteId);

    void delete(Note note);
}
