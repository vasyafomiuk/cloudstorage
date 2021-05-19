package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.forms.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NotesController {
    private static final Logger logger = LoggerFactory.getLogger(NotesController.class);
    private final NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping("/notes")
    public String getNotes(@ModelAttribute("noteForm") NoteForm noteForm,
                           Model model,
                           @AuthenticationPrincipal User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.error("There was a error " + bindingResult);
            return "home";
        }
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("credentialsForm", new CredentialsForm());
        Note note = new Note();
        note.setNoteTitle(noteForm.getNoteTitle());
        note.setNoteDescription(noteForm.getNoteDescription());
        note.setUserId(user.getUserId());
        note.setNoteId(noteForm.getNoteId());
        notesService.insertNote(note);
        model.addAttribute("showNotes", true);
        return "redirect:/home";
    }

    @GetMapping("/notes/{noteId}/delete")
    public String deleteNote(@PathVariable int noteId, ModelAndView modelAndView) {
        notesService.delete(noteId);
        return "redirect:/home";
    }

}
