package com.gmail.plai2.ying.fitjournal.room;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gmail.plai2.ying.fitjournal.ui.workout.strength_session.StrengthSessionFragment;

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
                    "Squash", "Hockey", "Treadmill", "Jumping Jacks", "Burpees", "Mountain Climbers", "Bear Crawls", "Ping Pong", "Racketball", "Frisbee", "Golf", "Mini-golf"};
            String strength[] = {"Bench Press", "Squat", "Leg Press", "Lunge", "Deadlift", "Leg Extension", "Leg Curl", "Standing Calf Raises", "Seat Calf Raises", "Chest Fly", "Push-up", "Pull-down", "Pull-up",
                    "Bent-over Row", "Upright Row", "Shoulder Press", "Shoulder fly", "Lateral Raise", "Shoulder Shrug", "Pushdown", "Triceps Extension", "Biceps Curl", "Crunch", "Russian Twist", "Leg Raise", "Back Extension"};
            List<String> listOfCardio = Arrays.asList(cardio);
            List<String> listOfStrength = Arrays.asList(strength);
            Set<String> setOfCardio = new TreeSet<String>(listOfCardio);
            Set<String> setOfStrength = new TreeSet<String>(listOfStrength);
            for (String exerciseName: setOfCardio) {
                mAvailableExerciseDao.insert(new AvailableExerciseItem(ExerciseType.CARDIO, exerciseName, false, false));
            }
            for (String exerciseName: setOfStrength) {
                mAvailableExerciseDao.insert(new AvailableExerciseItem(ExerciseType.STRENGTH, exerciseName, false, false));
            }
            return null;
        }
    }
}
