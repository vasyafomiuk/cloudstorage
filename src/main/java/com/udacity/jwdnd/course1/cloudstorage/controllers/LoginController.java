package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.forms.LoginForm;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("loginForm") LoginForm loginForm, Model model) {
        model.addAttribute("loginForm", new LoginForm());
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        boolean auth = userService.authenticate(username, password);
        if (!auth) {
            logger.info("login failed");
            model.addAttribute("error", true);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials");
        }
        logger.error("login successful.");
        return "login";
    }
}
