package com.feriramara.mytodo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    private static final String TAG = "NoteRepository";

    private NoteDao noteDao;

    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> todayNotes;
    private LiveData<List<Note>> todayNotesByDate;
    private LiveData<List<Note>> tomorrowNotes;
    private LiveData<List<Note>> tomorrowNotesByDate;
    private LiveData<List<Note>> somedayNotes;
    private LiveData<List<Note>> somedayNotesByDate;
    private LiveData<Integer> getCount;


    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);

        noteDao = database.noteDao();

        allNotes = noteDao.getAllNotes();

        todayNotes = noteDao.getTodayNotes();
        todayNotesByDate = noteDao.getTodayNotesByDate();

        tomorrowNotes = noteDao.getTomorrowNotes();
        tomorrowNotesByDate = noteDao.getTomorrowNotesByDate();

        somedayNotes = noteDao.getSomedayNotes();
        somedayNotesByDate = noteDao.getSomedayNotesByDate();

        getCount = noteDao.getDataCount();


    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
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

    public LiveData<Integer> getGetCount() {
        return getCount;
    }




    public static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    public static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    public static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }



}







