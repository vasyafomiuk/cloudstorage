package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMappeer;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotesService {
    private final NotesMappeer notesMappeer;

    public NotesService(NotesMappeer notesMappeer) {
        this.notesMappeer = notesMappeer;
    }

    public Note insertNote(Note note) {
        return notesMappeer.save(note);
    }

    public List<Note> getNotes() {
        return notesMappeer.findALlByOrderByNoteIdAsc();
    }

    public List<Note> findAllByNoteTitle(String noteTitle){
        return notesMappeer.findALlByNoteTitleOrderByNoteIdAsc(noteTitle);
    }

    public void delete(int noteId) {
        Note note = notesMappeer.findNoteByNoteId(noteId);
        notesMappeer.delete(note);
    }
}
