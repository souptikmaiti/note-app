package com.example.architecturemvvmexample1;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase dbInstance;   //singleton class

    public abstract NoteDao noteDao(); // abstract method to get NoteDao obj implemented by Room internally

    public static synchronized NoteDatabase getInstance(Context context){  //only one thread can access this method at a particular moment
        if(dbInstance == null){
            dbInstance = Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()   //when version is changed, Db will be destroyed and will be created from scratch
                    .addCallback(roomCallback)          //callback method will be called after db instance will be created to populate some initial data
                    .build();

        }
        return dbInstance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(dbInstance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{  //populate initial data by background thread
        private NoteDao noteDao;
        PopulateDbAsyncTask(NoteDatabase noteDatabase){
            this.noteDao = noteDatabase.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insertNote(new Note("Title 1","Description 1",1));
            noteDao.insertNote(new Note("Title 2","Description 2",2));
            noteDao.insertNote(new Note("Title 3","Description 3",3));
            noteDao.insertNote(new Note("Title 4","Description 4",4));
            return null;
        }
    }
}
