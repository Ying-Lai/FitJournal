package com.gmail.plai2.ying.fitjournal.room;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Database(entities = {Exercise.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters({TypeConverters.class})

public abstract class ExerciseDatabase extends RoomDatabase {

    private static ExerciseDatabase instance;

    public abstract ExerciseDao exerciseDao();

    public static synchronized ExerciseDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ExerciseDatabase.class, "exercise_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExerciseDao exerciseDao;

        private PopulateDbAsyncTask(ExerciseDatabase db) {
            exerciseDao = db.exerciseDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // Work in progress.
            List<Set> testSets = new ArrayList<>();
            testSets.add(new Set(6,20));
            testSets.add(new Set(7,20));
            testSets.add(new Set(8,20));
            exerciseDao.insert(new Exercise(Exercise.ExerciseType.CARDIO, "Jogging", new Date(), 20, Exercise.Intensity.HIGH));
            exerciseDao.insert(new Exercise(Exercise.ExerciseType.STRENGTH, "Chest Press", new Date(), testSets));
            return null;
        }
    }
}
