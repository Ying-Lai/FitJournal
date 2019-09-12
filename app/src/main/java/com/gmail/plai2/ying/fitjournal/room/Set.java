package com.gmail.plai2.ying.fitjournal.room;

public class Set {

    // Fields
    private static int EMPTY = -1;
    private static int BODY_WEIGHT = -2;
    private ExerciseType mType;
    private int mReps;
    private int mWeight;
    private boolean mIsEmpty;

    // Empty set constructor
    public Set(ExerciseType exerciseType) {
        mType = exerciseType;
        mReps = EMPTY;
        mIsEmpty = true;
        switch(mType) {
            case STRENGTH:
                mWeight = EMPTY;
                break;
            case CALISTHENICS:
                mWeight = BODY_WEIGHT;
                break;
        }
    }

    // Strength set constructor
    public Set(int reps, int weight) {
        mType = ExerciseType.STRENGTH;
        mReps = reps;
        mWeight = weight;
        mIsEmpty = false;
    }

    // Calisthenics set constructor
    public Set(int reps) {
        mType = ExerciseType.CALISTHENICS;
        mReps = reps;
        mWeight = BODY_WEIGHT;
        mIsEmpty = false;
    }

    // Setters and Getters
    public void setReps(int reps) {
        mReps = reps;
    }

    public int getReps() {
        return mReps;
    }

    public void setWeight(int weight) {
        mWeight = weight;
    }

    public int getWeight() {
        return mWeight;
    }

    public boolean isEmpty() {
        return mIsEmpty;
    }

    public void setEmpty(boolean empty) {
        if (empty) {
            mReps = EMPTY;
            if (mType == ExerciseType.STRENGTH) {
                mWeight = EMPTY;
            }
        }
        mIsEmpty = empty;
    }
}
