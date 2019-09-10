package com.gmail.plai2.ying.fitjournal.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "available_exercise_table")
public class AvailableExerciseItem {

    @PrimaryKey(autoGenerate = true)
    public int mID; // Look into this later -> Can't find getter for mID

    @ColumnInfo(name = "exercise_type")
    private ExerciseType mExerciseType;

    @ColumnInfo(name = "exercise_name")
    private String mExerciseName;

    @ColumnInfo(name = "favorited")
    private boolean mFavorited;

    @ColumnInfo(name = "custom")
    private boolean mCustom;

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

    public AvailableExerciseItem() {
        this.mExerciseType = null;
        this.mExerciseName = "";
        this.mFavorited = false;
        this.mCustom = false;
    }

    public AvailableExerciseItem(ExerciseType type, String exerciseName, boolean favorited, boolean custom) {
        this.mExerciseType = type;
        this.mExerciseName = exerciseName;
        this.mFavorited = favorited;
        this.mCustom = custom;
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

    public boolean isFavorited() {
        return mFavorited;
    }

    public void setFavorited(boolean favorited) {
        this.mFavorited = favorited;
    }

    public boolean isCustom() {
        return mCustom;
    }

    public void setCustom(boolean custom) {
        this.mCustom = custom;
    }
}
