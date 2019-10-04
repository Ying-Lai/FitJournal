package com.gmail.plai2.ying.fitjournal.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.threeten.bp.LocalDate;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Set;
import java.util.TreeSet;

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
        private PopulateDbAsyncTask(FitJournalDatabase db) {
            mAvailableExerciseDao = db.availableExerciseDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            // Add initial available exercise info
            String[] cardio = {"Walking", "Jogging", "Cycling", "Swimming", "Rowing", "Dancing", "Tennis", "Kickboxing", "Stair Climbing", "Jump Rope", "Skating", "Basketball", "Football", "Soccer", "Rugby",
                    "Squash", "Hockey", "Treadmill", "Jumping Jacks", "Ping Pong", "Racquetball", "Frisbee", "Golf", "Mini-golf"};
            String[] strength = {"Bench Press", "Weighted Squats", "Leg Press", "Deadlift", "Leg Extension", "Leg Curl", "Standing Calf Raises", "Seat Calf Raises", "Chest Fly", "Bent-over Row", "Upright Row",
                    "Shoulder Press", "Shoulder Fly", "Lateral Raise", "Shoulder Shrug", "Triceps Extension", "Biceps Curl", "Weighted Crunch", "Weighted Leg Raise", "Back Extension"};
            String[] calisthenics = {"Muscle-ups", "Squat Jumps", "Front Lever", "Push-ups", "Pull-ups", "Chin-ups", "Squats", "Back Lever", "Handstand", "Dips", "Hyper-extensions", "Leg Raises", "Planks",
                    "Burpees", "L-sits", "Lunge", "Crunch", "Russian Twist", "Mountain Climbers", "Bear Crawls"};

            Set<String> setOfCardio = new TreeSet<>(Arrays.asList(cardio));
            Set<String> setOfStrength = new TreeSet<>(Arrays.asList(strength));
            Set<String> setOfCalisthenics = new TreeSet<>(Arrays.asList(calisthenics));

            for (String exerciseName: setOfCardio) {
                mAvailableExerciseDao.insert(new AvailableExerciseItem(ExerciseType.CARDIO, exerciseName, false, false));
            }
            for (String exerciseName: setOfStrength) {
                mAvailableExerciseDao.insert(new AvailableExerciseItem(ExerciseType.STRENGTH, exerciseName, false, false));
            }
            for (String exerciseName: setOfCalisthenics) {
                mAvailableExerciseDao.insert(new AvailableExerciseItem(ExerciseType.CALISTHENICS, exerciseName, false, false));
            }
            return null;
        }
    }
}
