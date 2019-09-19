package com.gmail.plai2.ying.fitjournal.room;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Database(entities = {CompletedExerciseItem.class, AvailableExerciseItem.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters({TypeConverters.class})

public abstract class ExerciseDatabase extends RoomDatabase {

    // Fields
    private static ExerciseDatabase sInstance;
    public abstract AvailableExerciseDao availableExerciseDao();
    public abstract CompletedExerciseDao completedExerciseDao();

    // Singleton constructor
    public static synchronized ExerciseDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), ExerciseDatabase.class, "exercise_database")
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
        private PopulateDbAsyncTask(ExerciseDatabase db) {
            mAvailableExerciseDao = db.availableExerciseDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
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
            return null;
        }
    }
}
