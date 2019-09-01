package com.gmail.plai2.ying.fitjournal;

public class Set {

    private int reps;
    private int weight;

    public Set() {
        this.reps = 0;
        this.weight = 0;
    }
    public Set(int reps, int weight) {
        this.reps = reps;
        this.weight = weight;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getReps() {
        return reps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
