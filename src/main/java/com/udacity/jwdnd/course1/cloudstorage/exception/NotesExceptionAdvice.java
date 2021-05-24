package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.hibernate.JDBCException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class NotesExceptionAdvice {

    @ExceptionHandler(JDBCException.class)
    public String handleError1(JDBCException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getCause().getMessage());
        return "redirect:/home";
    }
}
