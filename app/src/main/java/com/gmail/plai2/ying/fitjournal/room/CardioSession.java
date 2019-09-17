package com.gmail.plai2.ying.fitjournal.room;

public class CardioSession {

    // Fields
    private static int EMPTY = -1;
    private int mDuration;
    private int mIntensity;
    private boolean mIsEmpty;
    private boolean mChecked;
    private int mId;
    static private int sId = 0;

    // Empty session
    public CardioSession() {
        mDuration = EMPTY;
        mIntensity = EMPTY;
        mIsEmpty = true;
        mChecked = false;
        mId = sId++;
    }

    // General cardioSession constructor
    public CardioSession(int duration, int intensity) {
        mDuration = duration;
        mIntensity = intensity;
        mIsEmpty = false;
        mChecked = false;
        mId = sId++;
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

    public int getId() {
        return mId;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public boolean isChecked() {
        return mChecked;
    }
}
