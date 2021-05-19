package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.forms.CredentialsForm;
import com.udacity.jwdnd.course1.cloudstorage.forms.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.forms.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private final FileService fileService;
    private final NotesService notesService;
    private final CredentialsService credentialsService;

    public HomeController(FileService fileService, NotesService notesService, CredentialsService credentialsService) {
        this.fileService = fileService;
        this.notesService = notesService;
        this.credentialsService = credentialsService;
    }

    @GetMapping("/home")
    public String getHome(CredentialsForm credentialsForm, NoteForm noteForm, Model model) {
        model.addAttribute("credentialsForm", new CredentialsForm());
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("fileForm", new FileForm());
        model.addAttribute("files", fileService.getFiles());
        model.addAttribute("credentials", credentialsService.getCredentials());
        model.addAttribute("notes", notesService.getNotes());
        return "home";
    }

}
