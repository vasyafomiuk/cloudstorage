package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.repository.FileRepository;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File insertFile(File file) {
        return fileRepository.save(file);
    }

    public List<File> getFiles() {
        return fileRepository.findAll();
    }

    public File getFile(int fileId) {
        return fileRepository.findFileByFileId(fileId);
    }

    public void delete(int fileId) {
        File file = getFile(fileId);
        fileRepository.delete(file);
    }

    public List<File> findFiles(String fileName) {
        return fileRepository.findFileByFileName(fileName);
    }
}
