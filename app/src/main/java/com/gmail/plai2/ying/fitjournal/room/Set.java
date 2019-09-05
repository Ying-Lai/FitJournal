package com.gmail.plai2.ying.fitjournal.room;

public class Set {

    private int mReps;
    private int mWeight;

    public Set() {
        this.mReps = 0;
        this.mWeight = 0;
    }
    public Set(int reps, int weight) {
        this.mReps = reps;
        this.mWeight = weight;
    }

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
}
