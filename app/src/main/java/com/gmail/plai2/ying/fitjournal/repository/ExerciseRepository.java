package com.gmail.plai2.ying.fitjournal.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.gmail.plai2.ying.fitjournal.room.Exercise;
import com.gmail.plai2.ying.fitjournal.room.ExerciseDao;
import com.gmail.plai2.ying.fitjournal.room.ExerciseDatabase;

import java.util.Date;
import java.util.List;

public class ExerciseRepository {
    private ExerciseDao exerciseDao;
    private LiveData<List<Exercise>> allExercises;
    private LiveData<List<Exercise>> allExercisesByDate;

    public ExerciseRepository(Application application) {
        ExerciseDatabase database = ExerciseDatabase.getInstance(application);
        exerciseDao = database.exerciseDao();
        allExercises = exerciseDao.getAllExercises();
    }

    public void insert(Exercise exercise) {
        new InsertExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void update(Exercise exercise) {
        new UpdateExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void delete(Exercise exercise) {
        new DeleteExerciseAsyncTask(exerciseDao).execute(exercise);
    }

    public void deleteAllExerciseByDate(Date date) {
        new DeleteAllExerciseByDateAsyncTask(exerciseDao).execute(date);
    }

    public void deleteAllExercises() {
        new DeleteAllExerciseAsyncTask(exerciseDao).execute();
    }

    public LiveData<List<Exercise>> getExerciseByDate(Date date) { return exerciseDao.getExerciseByDate(date); }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }

    private static class InsertExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {

        private ExerciseDao exerciseDao;

        private InsertExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercise) {
            exerciseDao.insert(exercise[0]);
            return null;
        }
    }

    private static class UpdateExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {

        private ExerciseDao exerciseDao;

        private UpdateExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercise) {
            exerciseDao.update(exercise[0]);
            return null;
        }
    }

    private static class DeleteExerciseAsyncTask extends AsyncTask<Exercise, Void, Void> {

        private ExerciseDao exerciseDao;

        private DeleteExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Exercise... exercise) {
            exerciseDao.delete(exercise[0]);
            return null;
        }
    }

    private static class DeleteAllExerciseByDateAsyncTask extends AsyncTask<Date, Void, Void> {

        private ExerciseDao exerciseDao;

        private DeleteAllExerciseByDateAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Date... dates) {
            exerciseDao.deleteAllExerciseByDate(dates[0]);
            return null;
        }
    }

    private static class DeleteAllExerciseAsyncTask extends AsyncTask<Void, Void, Void> {

        private ExerciseDao exerciseDao;

        private DeleteAllExerciseAsyncTask(ExerciseDao exerciseDao) {
            this.exerciseDao = exerciseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            exerciseDao.deleteAllExercises();
            return null;
        }
    }
}
