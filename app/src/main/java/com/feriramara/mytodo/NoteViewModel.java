package com.feriramara.mytodo;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;

    public LiveData<List<Note>> allNotes;
    public LiveData<List<Note>> todayNotes;
    public LiveData<List<Note>> todayNotesByDate;
    public LiveData<List<Note>> tomorrowNotes;
    public LiveData<List<Note>> tomorrowNotesByDate;
    public LiveData<List<Note>> somedayNotes;
    public LiveData<List<Note>> somedayNotesByDate;
    private LiveData<Integer> getCount;


    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);

        allNotes = repository.getAllNotes();

        todayNotes = repository.getTodayNotes();
        todayNotesByDate = repository.getTodayNotesByDate();

        tomorrowNotes = repository.getTomorrowNotes();
        tomorrowNotesByDate = repository.getTomorrowNotesByDate();

        somedayNotes = repository.getSomedayNotes();
        somedayNotesByDate = repository.getSomedayNotesByDate();

        getCount = repository.getGetCount();

    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }







    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getTodayNotes() {
        return todayNotes;
    }

    public LiveData<List<Note>> getTodayNotesByDate() {
        return todayNotesByDate;
    }

    public LiveData<List<Note>> getTomorrowNotes() {
        return tomorrowNotes;
    }

    public LiveData<List<Note>> getTomorrowNotesByDate() {
        return tomorrowNotesByDate;
    }

    public LiveData<List<Note>> getSomedayNotes() {
        return somedayNotes;
    }

    public LiveData<List<Note>> getSomedayNotesByDate() {
        return somedayNotesByDate;
    }




}
