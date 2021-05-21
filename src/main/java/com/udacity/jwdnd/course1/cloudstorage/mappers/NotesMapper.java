package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Insert("INSERT INTO notes (note_title, note_description, user_id)" +
            "VALUES ('#{noteTitle}', #{noteDescription}, #{'userId'})")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "user_title", property = "userTitle"),
            @Result(column = "user_description", property = "userDescription"),
            @Result(column = "user_id", property = "userId")
    })
    Note save(Note note);

    @Select("SELECT * FROM notes ORDER BY note_id ASC")
    @Results(id = "notes", value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "user_title", property = "userTitle"),
            @Result(column = "user_description", property = "userDescription"),
            @Result(column = "user_id", property = "userId")
    })
    @Flush
    List<Note> findALlByOrderByNoteIdAsc();

    @Select("SELECT * FROM notes WHERE note_title = #{noteTitle}")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "user_title", property = "userTitle"),
            @Result(column = "user_description", property = "userDescription"),
            @Result(column = "user_id", property = "userId")
    })
    @Flush
    List<Note> findALlByNoteTitleOrderByNoteIdAsc(String noteTitle);

    @Select("SELECT * FROM notes WHERE note_id = #{noteId}")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "user_title", property = "userTitle"),
            @Result(column = "user_description", property = "userDescription"),
            @Result(column = "user_id", property = "userId")
    })
    Note findNoteByNoteId(int noteId);

    @Delete("DELETE FROM notes WHERE note_id = #{noteId}")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "user_title", property = "userTitle"),
            @Result(column = "user_description", property = "userDescription"),
            @Result(column = "user_id", property = "userId")
    })
    int delete(int noteId);
}
