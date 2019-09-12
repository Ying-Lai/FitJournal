package com.gmail.plai2.ying.fitjournal.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "available_exercise_table")
public class AvailableExerciseItem {

    // Fields
    @ColumnInfo(name = "exercise_type")
    private ExerciseType mExerciseType;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "exercise_name")
    private String mExerciseName;

    @ColumnInfo(name = "favorited")
    private boolean mFavorited;

    @ColumnInfo(name = "custom")
    private boolean mCustom;

    // Empty constructor
    public AvailableExerciseItem() {
    }

    // General constructor for available exercise item
    public AvailableExerciseItem(ExerciseType type, String exerciseName, boolean favorited, boolean custom) {
        mExerciseType = type;
        mExerciseName = exerciseName;
        mFavorited = favorited;
        mCustom = custom;
    }

    // Getters and setters for fields
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

    // Other methods
}