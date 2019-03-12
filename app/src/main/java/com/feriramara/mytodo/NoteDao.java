package com.feriramara.mytodo;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface NoteDao {

    @Insert
    void insert(Note note);


    @Update
    void update(Note note);

    @Delete
    void delete(Note note);




    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note_table WHERE (section = 1) ORDER BY priority DESC")
    LiveData<List<Note>> getTodayNotes();

    @Query("SELECT * FROM note_table WHERE (section = 1) ORDER BY mDate DESC")
    LiveData<List<Note>> getTodayNotesByDate();

    @Query("SELECT * FROM note_table WHERE (section = 2) ORDER BY priority DESC")
    LiveData<List<Note>> getTomorrowNotes();

    @Query("SELECT * FROM note_table WHERE (section = 2) ORDER BY mDate DESC")
    LiveData<List<Note>> getTomorrowNotesByDate();

    @Query("SELECT * FROM note_table WHERE (section = 3) ORDER BY priority DESC")
    LiveData<List<Note>> getSomedayNotes();

    @Query("SELECT * FROM note_table WHERE (section = 3) ORDER BY mDate DESC")
    LiveData<List<Note>> getSomedayNotesByDate();


    @Query("SELECT COUNT(id) FROM note_table")
    LiveData<Integer> getDataCount();

}
