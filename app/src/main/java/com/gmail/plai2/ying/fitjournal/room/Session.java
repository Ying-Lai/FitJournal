package com.gmail.plai2.ying.fitjournal.room;

import java.util.List;

public class Session {

    // Static fields
    private static int EMPTY = -1;
    private static int BODY_WEIGHT = -2;
    static private int sId = 0;

    // Non-static fields
    private ExerciseType mType;
    private int mId;
    private int mReps;
    private int mWeight;
    private int mDuration;
    private int mIntensity;
    private boolean mIsEmpty;
    private boolean mChecked;

    // Empty session constructor
    public Session(ExerciseType exerciseType) {
        mType = exerciseType;
        mIsEmpty = true;
        mChecked = false;
        mId = sId++;
        switch (mType) {
            case CALISTHENICS:
                mReps = EMPTY;
                mWeight = BODY_WEIGHT;
                break;
            case CARDIO:
                mDuration = EMPTY;
                mIntensity = EMPTY;
                break;
            case STRENGTH:
                mReps = EMPTY;
                mWeight = EMPTY;
                break;
        }
    }

    // Session constructor
    public Session(ExerciseType exerciseType, int param1, int param2) {
        mType = exerciseType;
        switch (mType) {
            case CALISTHENICS:
                mReps = param1;
                mWeight = BODY_WEIGHT;
                break;
            case CARDIO:
                mDuration = param1;
                mIntensity = param2;
                break;
            case STRENGTH:
                mReps = param1;
                mWeight = param2;
                break;
        }
        mIsEmpty = false;
        mChecked = false;
        mId = sId++;
    }

    // Setters and Getters
    public int getId() {
        return mId;
    }

    public ExerciseType getType() {
        return mType;
    }

    public void setType(ExerciseType type) {
        mType = type;
    }

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

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setIntensity(int intensity) {
        mIntensity = intensity;
    }

    public int getIntensity() {
        return mIntensity;
    }

    public boolean isEmpty() {
        return mIsEmpty;
    }

    public void setEmpty(boolean empty) {
        mIsEmpty = empty;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public boolean isChecked() {
        return mChecked;
    }

    // Other methods
    public boolean compareSession(Session anotherSession) {
        if (this.getType() != anotherSession.getType()) {
            // Throw exception --- to do.
            return false;
        } else {
            switch (this.getType()) {
                case CALISTHENICS:
                case STRENGTH:
                    if (this.getReps() != anotherSession.getReps() && this.getWeight() != anotherSession.getWeight()) {
                        return false;
                    }
                    break;
                case CARDIO:
                    if (this.getDuration() != anotherSession.getDuration() && this.getIntensity() != anotherSession.getIntensity()) {
                        return false;
                    }
                    break;
            }
        }
        return true;
    }
}


