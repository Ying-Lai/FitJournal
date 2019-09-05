package com.gmail.plai2.ying.fitjournal.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey(autoGenerate = true)
    public int mID; // Look into this later -> Can't find getter for mID

    @ColumnInfo(name = "exercise_type")
    private ExerciseType mExerciseType;

    @ColumnInfo(name = "exercise_name")
    private String mExerciseName;

    @ColumnInfo(name = "exercise_date")
    private Date mExerciseDate;

    @ColumnInfo(name = "list_of_sets")
    private List<Set> mListOfSets;

    private Intensity mIntensity;

    public enum ExerciseType {
        CARDIO(0),
        STRENGTH(1);

        private int mCategory;

        ExerciseType(int category) {
            this.mCategory = category;
        }

        public int getCategory() {
            return mCategory;
        }
    }
    public enum Intensity {
        LOW(0),
        MEDIUM(1),
        HIGH(2);

        private int mIntensityLevel;

        Intensity(int intensityLevel) {
            this.mIntensityLevel = intensityLevel;
        }

        public int getIntensityLevel() {
            return mIntensityLevel;
        }
    }

    private int mDuration;

    public Exercise() {
        this.mExerciseType = null;
        this.mExerciseName = "";
        this.mExerciseDate = new Date();
        this.mListOfSets = Collections.emptyList();
        this.mIntensity = Intensity.LOW;
        this.mDuration = 0;
    }

    public Exercise(ExerciseType type, String exerciseName, Date exerciseDate, int duration, Intensity insensityLevel) {
        this.mExerciseType = type;
        this.mExerciseName = exerciseName;
        this.mExerciseDate= exerciseDate;
        this.mListOfSets = Collections.emptyList();
        this.mDuration = duration;
        this.mIntensity = insensityLevel;
    }

    public Exercise(ExerciseType type, String exerciseName, Date exerciseDate, List<Set> listOfSets) {
        this.mExerciseType = type;
        this.mExerciseName = exerciseName;
        this.mExerciseDate= exerciseDate;
        this.mListOfSets = listOfSets;
        this.mDuration = 0;
        this.mIntensity = Intensity.LOW;
    }

    public void setId(int id) {
        this.mID = id;
    }

    public int getId() {
        return mID;
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

    public Intensity getIntensity() {
        return mIntensity;
    }

    public void setIntensity(Intensity intensity) {
        this.mIntensity = intensity;
    }

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public int getDuration() {
        return mDuration;
    }

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
