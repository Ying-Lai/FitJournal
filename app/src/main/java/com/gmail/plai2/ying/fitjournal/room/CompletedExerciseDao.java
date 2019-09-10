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
public interface CompletedExerciseDao {

    @Insert
    void insert(CompletedExerciseItem completedExerciseItem);

    @Update
    void update(CompletedExerciseItem completedExerciseItem);

    @Delete
    void delete(CompletedExerciseItem completedExerciseItem);

    @Query("DELETE FROM completed_exercise_table WHERE exercise_date = :date")
    void deleteAllCompletedExerciseByDate(Date date);

    @Query("DELETE FROM completed_exercise_table")
    void deleteAllCompletedExercises();

    @Query("SELECT * FROM completed_exercise_table WHERE exercise_date = :date")
    LiveData<List<CompletedExerciseItem>> getCompletedExerciseByDate(Date date);

    @Query("SELECT * FROM completed_exercise_table")
    LiveData<List<CompletedExerciseItem>> getAllCompletedExercises();

}
