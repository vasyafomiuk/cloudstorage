package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.forms.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import lombok.Getter;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Objects;

@Controller
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(HashService.class);
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile,
                             RedirectAttributes attributes,
                             @AuthenticationPrincipal User user) throws IOException {
        if (multipartFile.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            logger.error("Please select a file to upload.");
            return "redirect:/home";
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        if (fileService.findFiles(fileName).size() > 0) {
            attributes.addFlashAttribute("error", "File name not allowed.");
            return "redirect:/home";
        }
        File file = new File();
        file.setFileData(multipartFile.getBytes());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(multipartFile.getSize() + "");
        file.setFileName(fileName);
        file.setUserId(user.getUserId());
        fileService.insertFile(file);
        attributes.addFlashAttribute("success", "You successfully uploaded " + fileName + " file!");
        return "redirect:/home";
    }

    @GetMapping("/files/{fileId}/delete")
    public String deleteFile(@PathVariable int fileId,
                             RedirectAttributes redirectAttributes) {
        fileService.delete(fileId);
        redirectAttributes.addFlashAttribute("success", "You successfully deleted a file!");
        return "redirect:/home";
    }

    @GetMapping("/files/{fileId}/download")
    public void getFile(@PathVariable int fileId, HttpServletResponse resp) throws IOException {
        File file = fileService.getFile(fileId);
        byte[] byteArray = file.getFileData();

        resp.setContentType(MimeTypeUtils.APPLICATION_OCTET_STREAM.getType());
        resp.setHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
        resp.setContentLength(byteArray.length);

        try (OutputStream os = resp.getOutputStream()) {
            os.write(byteArray, 0, byteArray.length);
        }
    }
}
