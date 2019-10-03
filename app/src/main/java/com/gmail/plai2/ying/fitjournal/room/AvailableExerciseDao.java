package com.gmail.plai2.ying.fitjournal.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AvailableExerciseDao {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(AvailableExerciseItem availableExerciseItem);

    @Update (onConflict = OnConflictStrategy.REPLACE)
    void update(AvailableExerciseItem availableExerciseItem);

    @Delete
    void delete(AvailableExerciseItem availableExerciseItem);

    @Query("DELETE FROM available_exercise_table")
    void deleteAllAvailableExercises();

    @Query("SELECT * FROM available_exercise_table WHERE  custom = :custom AND exercise_type = :exerciseType")
    LiveData<List<AvailableExerciseItem>> getAllCustomAvailableExercise(boolean custom, ExerciseType exerciseType);


    @Query("SELECT * FROM available_exercise_table WHERE  favorited = :favorited AND exercise_type = :exerciseType")
    LiveData<List<AvailableExerciseItem>> getAllFavoriteAvailableExercise(boolean favorited, ExerciseType exerciseType);

    @Query("SELECT * FROM available_exercise_table WHERE exercise_type = :exerciseType")
    LiveData<List<AvailableExerciseItem>> getAllAvailableExercises(ExerciseType exerciseType);

}
