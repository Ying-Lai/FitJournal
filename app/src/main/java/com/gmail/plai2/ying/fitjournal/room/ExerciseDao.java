package com.gmail.plai2.ying.fitjournal.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gmail.plai2.ying.fitjournal.room.Exercise;

import java.util.Date;
import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    void insert(Exercise exercise);

    @Update
    void update(Exercise exercise);

    @Delete
    void delete(Exercise exercise);

    @Query("DELETE FROM exercise_table WHERE exercise_date = :date")
    void deleteAllExerciseByDate(Date date);

    @Query("DELETE FROM exercise_table")
    void deleteAllExercises();

    @Query("SELECT * FROM exercise_table WHERE exercise_date = :date")
    LiveData<List<Exercise>> getExerciseByDate(Date date);

    @Query("SELECT * FROM exercise_table")
    LiveData<List<Exercise>> getAllExercises();

}
