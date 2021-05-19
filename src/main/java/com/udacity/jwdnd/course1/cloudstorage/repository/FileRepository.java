package com.udacity.jwdnd.course1.cloudstorage.repository;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FileRepository extends CrudRepository<File, Long> {

    File save(File file);

    List<File> findAll();

    File findFileByFileId(int fileId);

    List<File> findFileByFileName(String fileName);

    void delete(File file);
}
