package com.feriramara.mytodo;

import com.feriramara.mytodo.Utility.DateConverter;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;


@Entity(tableName = "note_table")
public class Note{

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    @TypeConverters(DateConverter.class)
    private Date mDate;

    private String priority;

    private int section;



    public Note(String title, Date date, String priority, int section) {
        this.title = title;
        this.mDate = date;
        this.priority = priority;
        this.section = section;
    }


    public void setId(int id) {
        this.id = id;
    }



    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return mDate;
    }

    public int getSection() {
        return section;
    }

    public String getPriority() {
        return priority;
    }



}


