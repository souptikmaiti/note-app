package com.example.architecturemvvmexample1;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    public NoteRepository(Application application){
        NoteDatabase noteDatabase = NoteDatabase.getInstance(application.getApplicationContext());
        noteDao = noteDatabase.noteDao(); //although noteDao() is a abstract method, Room does its implementation internally
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note){   //need to do it in background thread
        new InsetAsyncTask(noteDao).execute(note);
    }
    public void update(Note note){   //need to do it in background thread
        new UpdateAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){   //need to do it in background thread
        new DeleteAsyncTask(noteDao).execute(note);
    }
    public void deleteAll(){         //need to do it in background thread
        new DeleteAllAsyncTask(noteDao).execute();
    }
    public LiveData<List<Note>> getAll(){  //for live data retrieve method Room does it in background thread
        return allNotes;
    }

    private static class InsetAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        public InsetAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insertNote(notes[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        public UpdateAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.updateNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;
        public DeleteAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private NoteDao noteDao;
        public DeleteAllAsyncTask(NoteDao noteDao){
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
