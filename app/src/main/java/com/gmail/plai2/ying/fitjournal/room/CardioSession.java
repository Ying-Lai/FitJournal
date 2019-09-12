package com.gmail.plai2.ying.fitjournal.room;

public class CardioSession {

    // Fields
    private static int EMPTY = -1;
    private int mDuration;
    private int mIntensity;
    private boolean mIsEmpty;

    // Empty session
    public CardioSession() {
        mDuration = EMPTY;
        mIntensity = EMPTY;
        mIsEmpty = true;
    }

    // General cardioSession constructor
    public CardioSession(int duration, int intensity) {
        mDuration = duration;
        mIntensity = intensity;
        mIsEmpty = false;
    }

    // Setters and Getters
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
        if (empty) {
            mDuration = EMPTY;
            mIntensity = EMPTY;
        }
        mIsEmpty = empty;
    }
}
