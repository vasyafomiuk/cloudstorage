package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class FileUploadExceptionAdvice {

    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getCause().getMessage());
        return "redirect:/home";
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleError2(MaxUploadSizeExceededException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getCause().getMessage());
        return "redirect:/home";
    }
}
