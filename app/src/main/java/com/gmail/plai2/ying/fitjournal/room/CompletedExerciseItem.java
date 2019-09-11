package com.gmail.plai2.ying.fitjournal.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity(tableName = "completed_exercise_table")
public class CompletedExerciseItem {

    // Fields
    @PrimaryKey(autoGenerate = true)
    private int mId; // Look into this later -> Can't find getter for mId

    @ColumnInfo(name = "exercise_type")
    private ExerciseType mExerciseType;

    @ColumnInfo(name = "exercise_name")
    private String mExerciseName;

    @ColumnInfo(name = "exercise_date")
    private Date mExerciseDate;

    @ColumnInfo(name = "list_of_sets")
    private List<Set> mListOfSets;

    private ExerciseIntensity mIntensity;

    private int mDuration;

    // Empty constructor
    public CompletedExerciseItem() {
        this.mExerciseType = null;
        this.mExerciseName = "";
        this.mExerciseDate = new Date();
        this.mListOfSets = Collections.emptyList();
        this.mIntensity = ExerciseIntensity.LOW;
        this.mDuration = 0;
    }

    // Constructor for cardio exercises
    public CompletedExerciseItem(ExerciseType type, String exerciseName, Date exerciseDate, int duration, ExerciseIntensity insensityLevel) {
        this.mExerciseType = type;
        this.mExerciseName = exerciseName;
        this.mExerciseDate= exerciseDate;
        this.mListOfSets = Collections.emptyList();
        this.mDuration = duration;
        this.mIntensity = insensityLevel;
    }

    // Constructor for strength exercises
    public CompletedExerciseItem(ExerciseType type, String exerciseName, Date exerciseDate, List<Set> listOfSets) {
        this.mExerciseType = type;
        this.mExerciseName = exerciseName;
        this.mExerciseDate= exerciseDate;
        this.mListOfSets = listOfSets;
        this.mDuration = 0;
        this.mIntensity = ExerciseIntensity.LOW;
    }

    // Getters and setters for fields
    public void setMId(int id) {
        this.mId = id;
    }

    public int getMId() {
        return mId;
    }

    public ExerciseType getExerciseType() {
        return mExerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.mExerciseType = exerciseType;
    }

    public void setExerciseName(String exerciseName) {
        this.mExerciseName = exerciseName;
    }

    public String getExerciseName() {
        return mExerciseName;
    }

    public Date getExerciseDate() {
        return mExerciseDate;
    }

    public void setExerciseDate(Date exerciseDate) {
        this.mExerciseDate = exerciseDate;
    }

    public void setListOfSets(List<Set> listOfSets) {
        this.mListOfSets = listOfSets;
    }

    public List<Set> getListOfSets() {
        return mListOfSets;
    }

    public ExerciseIntensity getIntensity() {
        return mIntensity;
    }

    public void setIntensity(ExerciseIntensity intensity) {
        this.mIntensity = intensity;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public int getDuration() {
        return mDuration;
    }

    // Other methods
    public int getMinRep(List<Set> listOfSets) {
        int min = listOfSets.get(0).getReps();
        for (int i=0; i<listOfSets.size(); i++) {
            if (listOfSets.get(i).getReps() < min) {
                min = listOfSets.get(i).getReps();
            }
        }
        return min;
    }

    public int getMaxRep(List<Set> listOfSets) {
        int max = listOfSets.get(0).getReps();
        for (int i=0; i<listOfSets.size(); i++) {
            if (listOfSets.get(i).getReps() > max) {
                max = listOfSets.get(i).getReps();
            }
        }
        return max;
    }

}
