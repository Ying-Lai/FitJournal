package com.gmail.plai2.ying.fitjournal.room;

public class Set {

    // Fields
    private static int EMPTY_SET = -1;
    private int mReps;
    private int mWeight;
    private boolean mIsEmpty;

    // Empty constructor
    public Set() {
        this.mReps = EMPTY_SET;
        this.mWeight = EMPTY_SET;
        this.mIsEmpty = true;
    }

    // General constructor
    public Set(int reps, int weight) {
        this.mReps = reps;
        this.mWeight = weight;
        this.mIsEmpty = false;
    }

    // Setters and Getters
    public void setReps(int reps) {
        this.mReps = reps;
    }

    public int getReps() {
        return mReps;
    }

    public void setWeight(int weight) {
        this.mWeight = weight;
    }

    public int getWeight() {
        return mWeight;
    }

    public boolean isEmpty() {
        return mIsEmpty;
    }

    public void setEmpty(boolean empty) {
        mReps = EMPTY_SET;
        mWeight = EMPTY_SET;
        mIsEmpty = empty;
    }
}
