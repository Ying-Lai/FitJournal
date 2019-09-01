package com.gmail.plai2.ying.fitjournal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "type_of_exercise")
    private String typeOfExercise;

    @ColumnInfo(name = "exercise_name")
    private String exerciseName;

    @ColumnInfo(name = "exercise_date")
    private Date exerciseDate;

    @ColumnInfo(name = "list_of_sets")
    private List<Set> numberOfSets;

    private int duration;

    public Exercise() {
        this.typeOfExercise = "";
        this.exerciseName = "";
        this.exerciseDate = new Date();
        this.numberOfSets = Collections.emptyList();
        this.duration = 0;
    }

    public Exercise(String type, String exerciseName, Date exerciseDate, List<Set> numberOfSets, int duration) {
        this.typeOfExercise = type;
        this.exerciseName = exerciseName;
        this.exerciseDate= exerciseDate;
        this.numberOfSets = numberOfSets;
        this.duration = duration;
    }

    public String getTypeOfExercise() {
        return typeOfExercise;
    }

    public void setTypeOfExercise(String typeOfExercise) {
        this.typeOfExercise = typeOfExercise;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public Date getExerciseDate() {
        return exerciseDate;
    }

    public void setExerciseDate(Date exerciseDate) {
        this.exerciseDate = exerciseDate;
    }

    public void setNumberOfSets(List<Set> numberOfSets) {
        this.numberOfSets = numberOfSets;
    }

    public List<Set> getNumberOfSets() {
        return numberOfSets;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
