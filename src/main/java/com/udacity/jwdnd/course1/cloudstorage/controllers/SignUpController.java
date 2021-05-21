package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.forms.SignUpForm;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.udacity.jwdnd.course1.cloudstorage.consts.ErrorMessages.*;


@Controller
public class SignUpController {
    private final Logger logger = LoggerFactory.getLogger(SignUpController.class);
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String getSignUp(@ModelAttribute("signupForm") SignUpForm signupForm, Model model) {
        model.addAttribute("signupForm", new SignUpForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String postSignUp(@ModelAttribute("signupForm") SignUpForm signupForm, Model model) {
        model.addAttribute("signupForm", new SignUpForm());
        if (signupForm.getUsername() == null) {
            return "home";
        }
        User user = new User();
        user.setPassword(signupForm.getPassword());
        user.setUsername(signupForm.getUsername());
        user.setFirstName(signupForm.getFirstName());
        user.setLastName(signupForm.getLastName());
        if (!userService.isUsernameAvailable(signupForm.getUsername())) {
            model.addAttribute("signupError", String.format(USERNAME_ALREADY_EXIST, signupForm.getUsername()));
            return "signup";
        }
        model.addAttribute("signupSuccess", true);
        userService.createUser(user);
        return "signup";
    }
}
