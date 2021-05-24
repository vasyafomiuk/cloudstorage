package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImp implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
