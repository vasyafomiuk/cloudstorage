package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;
import org.hibernate.type.BinaryType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO files " +
                "(" +
                    "file_name, " +
                    "content_type, " +
                    "file_size, " +
                    "user_id, " +
                    "file_data" +
                ")" +
            "VALUES " +
                "(" +
                    "#{fileName}," +
                    "#{contentType}," +
                    "#{fileSize}, " +
                    "#{userId}," +
                    "#{fileData}" +
                ")")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "file_size", property = "fileSize"),
            @Result(column = "file_data", property = "fileData"),
            @Result(column = "user_id", property = "userId")
    })
    @Options(useGeneratedKeys = true, keyColumn = "file_id")
    int save(File file);

    @Select("SELECT * FROM files WHERE user_id = #{userId}")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "file_size", property = "fileSize"),
            @Result(column = "file_data", property = "fileData"),
            @Result(column = "user_id", property = "userId")
    })
    List<File> findAll(int userId);

    @Select("SELECT * FROM files WHERE file_id = #{fileId}")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "file_size", property = "fileSize"),
            @Result(column = "file_data", property = "fileData"),
            @Result(column = "user_id", property = "userId")
    })
    File findFileByFileId(int fileId);

    @Select("SELECT file_name FROM files WHERE file_name = #{fileName}")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "file_size", property = "fileSize"),
            @Result(column = "file_data", property = "fileData"),
            @Result(column = "user_id", property = "userId")
    })
    List<File> findFileByFileName(String fileName);

    @Delete("DELETE FROM files WHERE file_id = #{fileId}")
    @Results(value = {
            @Result(column = "file_id", property = "fileId", id = true),
            @Result(column = "file_name", property = "fileName"),
            @Result(column = "content_type", property = "contentType"),
            @Result(column = "file_size", property = "fileSize"),
            @Result(column = "file_data", property = "fileData"),
            @Result(column = "user_id", property = "userId")
    })
    int delete(int fileId);
}

