package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.repository.NotesRepository;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotesService {
    private final NotesRepository notesRepository;

    public NotesService(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    public Note insertNote(Note note) {
        return notesRepository.save(note);
    }

    public List<Note> getNotes() {
        return notesRepository.findALlByOrderByNoteIdAsc();
    }

    public List<Note> findAllByNoteTitle(String noteTitle){
        return notesRepository.findALlByNoteTitleOrderByNoteIdAsc(noteTitle);
    }

    public void delete(int noteId) {
        Note note = notesRepository.findNoteByNoteId(noteId);
        notesRepository.delete(note);
    }
}
