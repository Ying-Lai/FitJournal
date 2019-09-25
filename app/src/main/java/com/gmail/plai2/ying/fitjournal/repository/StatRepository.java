package com.gmail.plai2.ying.fitjournal.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.gmail.plai2.ying.fitjournal.room.FitJournalDatabase;
import com.gmail.plai2.ying.fitjournal.room.Stat;
import com.gmail.plai2.ying.fitjournal.room.StatDao;

import java.time.LocalDate;
import java.util.List;

public class StatRepository {

    // Fields
    private StatDao mStatDao;
    private LiveData<List<Stat>> mAllStat;

    // Constructor
    public StatRepository(Application application) {
        FitJournalDatabase database = FitJournalDatabase.getInstance(application);
        mStatDao = database.statDao();
        mAllStat = mStatDao.getAllStats();
    }

    // Methods for StatDao
    public void insert(Stat stat) {
        new InsertStatAsyncTask(mStatDao).execute(stat);
    }

    public void update(Stat stat) {
        new UpdateStatAsyncTask(mStatDao).execute(stat);
    }

    public void delete(Stat stat) {
        new DeleteStatAsyncTask(mStatDao).execute(stat);
    }

    public void deleteStatByDate(LocalDate date) {
        new DeleteStatByDateAsyncTask(mStatDao).execute(date);
    }

    public void deleteAlStats() {
        new DeleteAllStatsAsyncTask(mStatDao).execute();
    }

    public LiveData<List<Stat>> getStatByDate(LocalDate date) { return mStatDao.getStatByDate(date); }

    public LiveData<List<Stat>> getAllStats() {
        return mAllStat;
    }

    // AsyncTasks for Stat
    private static class InsertStatAsyncTask extends AsyncTask<Stat, Void, Void> {

        private StatDao mStatDao;

        private InsertStatAsyncTask(StatDao statDao) {
            this.mStatDao = statDao;
        }

        @Override
        protected Void doInBackground(Stat... stats) {
            mStatDao.insert(stats[0]);
            return null;
        }
    }

    private static class UpdateStatAsyncTask extends AsyncTask<Stat, Void, Void> {

        private StatDao mStatDao;

        private UpdateStatAsyncTask(StatDao statDao) {
            this.mStatDao = statDao;
        }

        @Override
        protected Void doInBackground(Stat... stats) {
            mStatDao.update(stats[0]);
            return null;
        }
    }

    private static class DeleteStatAsyncTask extends AsyncTask<Stat, Void, Void> {

        private StatDao mStatDao;

        private DeleteStatAsyncTask(StatDao statDao) {
            this.mStatDao = statDao;
        }

        @Override
        protected Void doInBackground(Stat... stats) {
            mStatDao.delete(stats[0]);
            return null;
        }
    }

    private static class DeleteStatByDateAsyncTask extends AsyncTask<LocalDate, Void, Void> {

        private StatDao mStatDao;

        private DeleteStatByDateAsyncTask(StatDao statDao) {
            this.mStatDao = statDao;
        }

        @Override
        protected Void doInBackground(LocalDate... dates) {
            mStatDao.deleteStatByDate(dates[0]);
            return null;
        }
    }

    private static class DeleteAllStatsAsyncTask extends AsyncTask<Void, Void, Void> {

        private StatDao mStatDao;

        private DeleteAllStatsAsyncTask(StatDao statDao) {
            this.mStatDao = statDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mStatDao.deleteAllStats();
            return null;
        }
    }
}