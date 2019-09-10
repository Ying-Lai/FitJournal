package com.gmail.plai2.ying.fitjournal.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface AvailableExerciseDao {

    @Insert
    void insert(AvailableExerciseItem availableExerciseItem);

    @Update
    void update(AvailableExerciseItem availableExerciseItem);

    @Delete
    void delete(AvailableExerciseItem availableExerciseItem);

    @Query("DELETE FROM available_exercise_table")
    void deleteAllAvailableExercises();

    @Query("SELECT * FROM available_exercise_table WHERE  custom = :custom AND exercise_type = :exerciseType")
    LiveData<List<AvailableExerciseItem>> getAllCustomAvailableExercise(boolean custom, AvailableExerciseItem.ExerciseType exerciseType);


    @Query("SELECT * FROM available_exercise_table WHERE  favorited = :favorited AND exercise_type = :exerciseType")
    LiveData<List<AvailableExerciseItem>> getAllFavoritedAvailableExercise(boolean favorited, AvailableExerciseItem.ExerciseType exerciseType);

    @Query("SELECT * FROM available_exercise_table WHERE exercise_type = :exerciseType")
    LiveData<List<AvailableExerciseItem>> getAllAvailableExercises(AvailableExerciseItem.ExerciseType exerciseType);

}
