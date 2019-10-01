package com.gmail.plai2.ying.fitjournal.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseDao;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseDao;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.FitJournalDatabase;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;

import org.threeten.bp.LocalDate;

import java.util.List;

public class ExerciseRepository {

    // Fields
    private AvailableExerciseDao mAvailableExerciseDao;
    private CompletedExerciseDao mCompletedExerciseDao;
    private LiveData<List<CompletedExerciseItem>> mAllCompletedExercises;

    // Constructor
    public ExerciseRepository(Application application) {
        FitJournalDatabase database = FitJournalDatabase.getInstance(application);
        mAvailableExerciseDao = database.availableExerciseDao();
        mCompletedExerciseDao = database.completedExerciseDao();
        mAllCompletedExercises = mCompletedExerciseDao.getAllCompletedExercises();
    }

    // Methods for AvailableExerciseDao
    public void insert(AvailableExerciseItem availableExerciseItem) {
        new InsertAvailableExerciseAsyncTask(mAvailableExerciseDao).execute(availableExerciseItem);
    }

    public void update(AvailableExerciseItem availableExerciseItem) {
        new UpdateAvailableExerciseAsyncTask(mAvailableExerciseDao).execute(availableExerciseItem);
    }

    public void delete(AvailableExerciseItem availableExerciseItem) {
        new DeleteAvailableExerciseAsyncTask(mAvailableExerciseDao).execute(availableExerciseItem);
    }

    public void deleteAllAvailableExercises() {
        new DeleteAllAvailableExerciseAsyncTask(mAvailableExerciseDao).execute();
    }

    public LiveData<List<AvailableExerciseItem>> getAllCustomAvailableExercise(boolean custom, ExerciseType exerciseType) {
        return mAvailableExerciseDao.getAllCustomAvailableExercise(custom, exerciseType);
    }

    public LiveData<List<AvailableExerciseItem>> getAllAvailableFavoritedExercise(boolean favorited, ExerciseType exerciseType) {
        return mAvailableExerciseDao.getAllFavoritedAvailableExercise(favorited, exerciseType);
    }

    public LiveData<List<AvailableExerciseItem>> getAllAvailableExercises(ExerciseType exerciseType) {
        return mAvailableExerciseDao.getAllAvailableExercises(exerciseType);
    }

    // AsyncTasks for AvailableExerciseItem
    private static class InsertAvailableExerciseAsyncTask extends AsyncTask<AvailableExerciseItem, Void, Void> {

        private AvailableExerciseDao availableExerciseDao;

        private InsertAvailableExerciseAsyncTask(AvailableExerciseDao availableExerciseDao) {
            this.availableExerciseDao = availableExerciseDao;
        }

        @Override
        protected Void doInBackground(AvailableExerciseItem... availableExerciseItems) {
            availableExerciseDao.insert(availableExerciseItems[0]);
            return null;
        }
    }

    private static class UpdateAvailableExerciseAsyncTask extends AsyncTask<AvailableExerciseItem, Void, Void> {

        private AvailableExerciseDao availableExerciseDao;

        private UpdateAvailableExerciseAsyncTask(AvailableExerciseDao availableExerciseDao) {
            this.availableExerciseDao = availableExerciseDao;
        }

        @Override
        protected Void doInBackground(AvailableExerciseItem... availableExerciseItems) {
            availableExerciseDao.update(availableExerciseItems[0]);
            return null;
        }
    }

    private static class DeleteAvailableExerciseAsyncTask extends AsyncTask<AvailableExerciseItem, Void, Void> {

        private AvailableExerciseDao availableExerciseDao;

        private DeleteAvailableExerciseAsyncTask(AvailableExerciseDao availableExerciseDao) {
            this.availableExerciseDao = availableExerciseDao;
        }

        @Override
        protected Void doInBackground(AvailableExerciseItem... availableExerciseItems) {
            availableExerciseDao.delete(availableExerciseItems[0]);
            return null;
        }
    }

    private static class DeleteAllAvailableExerciseAsyncTask extends AsyncTask<Void, Void, Void> {

        private AvailableExerciseDao availableExerciseDao;

        private DeleteAllAvailableExerciseAsyncTask(AvailableExerciseDao availableExerciseDao) {
            this.availableExerciseDao = availableExerciseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            availableExerciseDao.deleteAllAvailableExercises();
            return null;
        }
    }

    // Methods for CompletedExerciseDao
    public void insert(CompletedExerciseItem completedExerciseItem) {
        new InsertCompletedExerciseAsyncTask(mCompletedExerciseDao).execute(completedExerciseItem);
    }

    public void update(CompletedExerciseItem completedExerciseItem) {
        new UpdateCompletedExerciseAsyncTask(mCompletedExerciseDao).execute(completedExerciseItem);
    }

    public void delete(CompletedExerciseItem completedExerciseItem) {
        new DeleteCompletedExerciseAsyncTask(mCompletedExerciseDao).execute(completedExerciseItem);
    }

    public void deleteAllCompletedExerciseByDate(LocalDate date) {
        new DeleteAllCompletedExerciseByDateAsyncTask(mCompletedExerciseDao).execute(date);
    }

    public void deleteAllCompletedExercises() {
        new DeleteAllCompletedExerciseAsyncTask(mCompletedExerciseDao).execute();
    }

    public LiveData<List<CompletedExerciseItem>> getAllCompletedExerciseByDate(LocalDate date) { return mCompletedExerciseDao.getCompletedExerciseByDate(date); }

    public LiveData<List<CompletedExerciseItem>> getAllCompletedExercises() {
        return mAllCompletedExercises;
    }

    // AsyncTasks for Completed Exercise Item
    private static class InsertCompletedExerciseAsyncTask extends AsyncTask<CompletedExerciseItem, Void, Void> {

        private CompletedExerciseDao completedExerciseDao;

        private InsertCompletedExerciseAsyncTask(CompletedExerciseDao completedExerciseDao) {
            this.completedExerciseDao = completedExerciseDao;
        }

        @Override
        protected Void doInBackground(CompletedExerciseItem... completedExerciseItem) {
            completedExerciseDao.insert(completedExerciseItem[0]);
            return null;
        }
    }

    private static class UpdateCompletedExerciseAsyncTask extends AsyncTask<CompletedExerciseItem, Void, Void> {

        private CompletedExerciseDao completedExerciseDao;

        private UpdateCompletedExerciseAsyncTask(CompletedExerciseDao completedExerciseDao) {
            this.completedExerciseDao = completedExerciseDao;
        }

        @Override
        protected Void doInBackground(CompletedExerciseItem... completedExerciseItem) {
            completedExerciseDao.update(completedExerciseItem[0]);
            return null;
        }
    }

    private static class DeleteCompletedExerciseAsyncTask extends AsyncTask<CompletedExerciseItem, Void, Void> {

        private CompletedExerciseDao completedExerciseDao;

        private DeleteCompletedExerciseAsyncTask(CompletedExerciseDao completedExerciseDao) {
            this.completedExerciseDao = completedExerciseDao;
        }

        @Override
        protected Void doInBackground(CompletedExerciseItem... completedExerciseItem) {
            completedExerciseDao.delete(completedExerciseItem[0]);
            return null;
        }
    }

    private static class DeleteAllCompletedExerciseByDateAsyncTask extends AsyncTask<LocalDate, Void, Void> {

        private CompletedExerciseDao completedExerciseDao;

        private DeleteAllCompletedExerciseByDateAsyncTask(CompletedExerciseDao completedExerciseDao) {
            this.completedExerciseDao = completedExerciseDao;
        }

        @Override
        protected Void doInBackground(LocalDate... dates) {
            completedExerciseDao.deleteAllCompletedExerciseByDate(dates[0]);
            return null;
        }
    }

    private static class DeleteAllCompletedExerciseAsyncTask extends AsyncTask<Void, Void, Void> {

        private CompletedExerciseDao completedExerciseDao;

        private DeleteAllCompletedExerciseAsyncTask(CompletedExerciseDao completedExerciseDao) {
            this.completedExerciseDao = completedExerciseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            completedExerciseDao.deleteAllCompletedExercises();
            return null;
        }
    }
}
