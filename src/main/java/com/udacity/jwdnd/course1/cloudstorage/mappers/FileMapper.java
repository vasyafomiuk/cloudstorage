package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO files (file_name, content_type, file_size, user_id, file_data)" +
            "VALUES ('#{fileName}, '#{contentType}', '#{fileSize}', '#{userId}', '#{fileData}')")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    File save(File file);

    @Select("SELECT * FROM files")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    List<File> findAll();

    @Select("SELECT * FROM files where file_id = #{fileId}")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    File findFileByFileId(int fileId);

    @Select("SELECT fileName FROM files WHERE file_name = #{fileName}")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    List<File> findFileByFileName(String fileName);

    @Delete("DELETE FROM files WHERE fileId = #{fileId}")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "user_id", property = "userId")
    })
    int delete(int fileId);
}

