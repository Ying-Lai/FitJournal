package com.gmail.plai2.ying.fitjournal.room;

import android.content.Context;
import android.os.AsyncTask;
import android.util.MonthDisplayHelper;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Calendar.MONTH;

@Database(entities = {CompletedExerciseItem.class, AvailableExerciseItem.class, Stat.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters({TypeConverters.class})

public abstract class FitJournalDatabase extends RoomDatabase {

    // Fields
    private static FitJournalDatabase sInstance;
    public abstract AvailableExerciseDao availableExerciseDao();
    public abstract CompletedExerciseDao completedExerciseDao();
    public abstract StatDao statDao();

    // Singleton constructor
    public static synchronized FitJournalDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), FitJournalDatabase.class, "fit_journal_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(sRoomCallBack)
                    .build();
        }
        return sInstance;
    }

    private static RoomDatabase.Callback sRoomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(sInstance).execute();
        }
    };

    // Initial available exercise items in the database
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AvailableExerciseDao mAvailableExerciseDao;
        private StatDao mStatDao;
        private CompletedExerciseDao mCompletedExerciseDao;
        private PopulateDbAsyncTask(FitJournalDatabase db) {
            mAvailableExerciseDao = db.availableExerciseDao();
            mStatDao = db.statDao();
            mCompletedExerciseDao = db.completedExerciseDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            // Add initial available exercise info
            String cardio[] = {"Walking", "Jogging", "Cycling", "Swimming", "Rowing", "Dancing", "Tennis", "Kickboxing", "Stair Climbing", "Jump Rope", "Skating", "Basketball", "Football", "Soccer", "Rugby",
                    "Squash", "Hockey", "Treadmill", "Jumping Jacks", "Ping Pong", "Racketball", "Frisbee", "Golf", "Mini-golf"};
            String strength[] = {"Bench Press", "Weighted Squats", "Leg Press", "Deadlift", "Leg Extension", "Leg Curl", "Standing Calf Raises", "Seat Calf Raises", "Chest Fly", "Bent-over Row", "Upright Row",
                    "Shoulder Press", "Shoulder Fly", "Lateral Raise", "Shoulder Shrug", "Triceps Extension", "Biceps Curl", "Weighted Crunch", "Weighted Leg Raise", "Back Extension"};
            String calisthenics[] = {"Muscle-ups", "Squat Jumps", "Front Lever", "Push-ups", "Pull-ups", "Chin-ups", "Squats", "Back Lever", "Handstand", "Dips", "Hyper-extensions", "Leg Raises", "Planks",
                    "Burpees", "L-sits", "Lunge", "Crunch", "Russian Twist", "Mountain Climbers", "Bear Crawls"};
            List<String> listOfCardio = Arrays.asList(cardio);
            List<String> listOfStrength = Arrays.asList(strength);
            List<String> listOfCalisthenics = Arrays.asList(calisthenics);
            Set<String> setOfCardio = new TreeSet<String>(listOfCardio);
            Set<String> setOfStrength = new TreeSet<String>(listOfStrength);
            Set<String> setOfCalisthenics = new TreeSet<String>(listOfCalisthenics);
            for (String exerciseName: setOfCardio) {
                mAvailableExerciseDao.insert(new AvailableExerciseItem(ExerciseType.CARDIO, exerciseName, false, false));
            }
            for (String exerciseName: setOfStrength) {
                mAvailableExerciseDao.insert(new AvailableExerciseItem(ExerciseType.STRENGTH, exerciseName, false, false));
            }
            for (String exerciseName: setOfCalisthenics) {
                mAvailableExerciseDao.insert(new AvailableExerciseItem(ExerciseType.CALISTHENICS, exerciseName, false, false));
            }

            // Add stats for testing
            LocalDate date1 = LocalDate.of(2019, Calendar.SEPTEMBER,12);
            mStatDao.insert(new Stat(date1, 148, 19));
            org.threeten.bp.LocalDate date2 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,13);
            mStatDao.insert(new Stat(date2, 155, 15));
            org.threeten.bp.LocalDate date3 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,14);
            mStatDao.insert(new Stat(date3, 142, 17));
            org.threeten.bp.LocalDate date4 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,15);
            mStatDao.insert(new Stat(date4, 111, 12));
            org.threeten.bp.LocalDate date5 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,16);
            mStatDao.insert(new Stat(date5, 148, 19));
            org.threeten.bp.LocalDate date6 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,17);
            mStatDao.insert(new Stat(date6, 155, 15));
            org.threeten.bp.LocalDate date7 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,18);
            mStatDao.insert(new Stat(date7, 142, 17));
            org.threeten.bp.LocalDate date8 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,19);
            mStatDao.insert(new Stat(date8, 111, 12));
            org.threeten.bp.LocalDate date9 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,20);
            mStatDao.insert(new Stat(date9, 148, 19));
            org.threeten.bp.LocalDate date10 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,21);
            mStatDao.insert(new Stat(date10, 155, 15));
            org.threeten.bp.LocalDate date11 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,22);
            mStatDao.insert(new Stat(date11, 142, 17));
            org.threeten.bp.LocalDate date12 = org.threeten.bp.LocalDate.of(2019, Calendar.SEPTEMBER,23);
            mStatDao.insert(new Stat(date12, 111, 12));

            List<Session> sess1 = new ArrayList<>();
            sess1.add(new Session(ExerciseType.CARDIO, 10, 10));
            mCompletedExerciseDao.insert(new CompletedExerciseItem(ExerciseType.CARDIO, "Running", date1, sess1, ""));
            mCompletedExerciseDao.insert(new CompletedExerciseItem(ExerciseType.CARDIO, "Running", date2, sess1, ""));
            mCompletedExerciseDao.insert(new CompletedExerciseItem(ExerciseType.CARDIO, "Running", date3, sess1, ""));
            mCompletedExerciseDao.insert(new CompletedExerciseItem(ExerciseType.CARDIO, "Running", date4, sess1, ""));
            mCompletedExerciseDao.insert(new CompletedExerciseItem(ExerciseType.CARDIO, "Running", date5, sess1, ""));
            mCompletedExerciseDao.insert(new CompletedExerciseItem(ExerciseType.CARDIO, "Running", date6, sess1, ""));
            mCompletedExerciseDao.insert(new CompletedExerciseItem(ExerciseType.CARDIO, "Running", date7, sess1, ""));

            return null;
        }
    }
}
