package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Insert("INSERT INTO notes (note_title, note_description, user_id)" +
            "VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "note_title", property = "noteTitle"),
            @Result(column = "note_description", property = "noteDescription"),
            @Result(column = "user_id", property = "userId")
    })
    @Options(useGeneratedKeys = true, keyColumn = "note_id")
    int save(Note note);

    @Update("UPDATE notes " +
            "SET note_title = #{noteTitle}, " +
            "note_description = #{noteDescription}, " +
            "user_id = #{userId}" +
            "WHERE note_id = #{noteId}")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "note_title", property = "noteTitle"),
            @Result(column = "note_description", property = "noteDescription"),
            @Result(column = "user_id", property = "userId")
    })
    void update(Note note);

    @Select("SELECT * FROM notes WHERE user_id = #{userId} ORDER BY note_id ASC")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "note_title", property = "noteTitle"),
            @Result(column = "note_description", property = "noteDescription"),
            @Result(column = "user_id", property = "userId")
    })
    List<Note> findALlByOrderByNoteIdAsc(int userId);

    @Select("SELECT * FROM notes")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "note_title", property = "noteTitle"),
            @Result(column = "note_description", property = "noteDescription"),
            @Result(column = "user_id", property = "userId")
    })
    List<Note> findALlNotes();

    @Select("SELECT * FROM notes WHERE note_title = #{noteTitle}")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "note_title", property = "noteTitle"),
            @Result(column = "note_description", property = "noteDescription"),
            @Result(column = "user_id", property = "userId")
    })
    List<Note> findALlByNoteTitleOrderByNoteIdAsc(String noteTitle);

    @Select("SELECT * FROM notes WHERE note_id = #{noteId}")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "note_title", property = "noteTitle"),
            @Result(column = "note_description", property = "noteDescription"),
            @Result(column = "user_id", property = "userId")
    })
    Note findNoteByNoteId(int noteId);

    @Delete("DELETE FROM notes WHERE note_id = #{noteId}")
    @Results(value = {
            @Result(column = "note_id", property = "noteId", id = true),
            @Result(column = "note_title", property = "noteTitle"),
            @Result(column = "note_description", property = "noteDescription"),
            @Result(column = "user_id", property = "userId")
    })
    int delete(int noteId);
}
