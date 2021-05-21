package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int insertFile(File file) {
        return fileMapper.save(file);
    }

    public List<File> getFiles() {
        return fileMapper.findAll();
    }

    public File getFile(int fileId) {
        return fileMapper.findFileByFileId(fileId);
    }

    public void delete(int fileId) {
        File file = getFile(fileId);
        fileMapper.delete(file.getFileId());
    }

    public List<File> findFiles(String fileName) {
        return fileMapper.findFileByFileName(fileName);
    }
}
