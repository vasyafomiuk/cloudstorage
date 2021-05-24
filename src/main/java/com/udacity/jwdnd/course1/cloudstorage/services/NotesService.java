package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {
    private final NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public int insertNote(Note note) {
        return notesMapper.save(note);
    }

    public void updateNote(Note note) {
        notesMapper.update(note);
    }

    public List<Note> getNotes(int userId) {
        return notesMapper.findALlByOrderByNoteIdAsc(userId);
    }

    public List<Note> findAllNotes() {
        return notesMapper.findALlNotes();
    }

    public List<Note> findAllByNoteTitle(String noteTitle) {
        return notesMapper.findALlByNoteTitleOrderByNoteIdAsc(noteTitle);
    }

    public void delete(int noteId) {
        Note note = notesMapper.findNoteByNoteId(noteId);
        notesMapper.delete(note.getNoteId());
    }
}
