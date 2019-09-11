package com.gmail.plai2.ying.fitjournal.room;

public enum ExerciseIntensity {
    LOW(0),
    MEDIUM(1),
    HIGH(2);

    // Fields
    private int mIntensityLevel;

    // Constructor
    ExerciseIntensity(int intensityLevel) {
        this.mIntensityLevel = intensityLevel;
    }

    // Getter
    public int getIntensityLevel() {
        return mIntensityLevel;
    }

    // Other methods
    public String getIntensityName() {
        String name = null;
        switch(mIntensityLevel) {
            case 0:
                name = "Low";
                break;
            case 1:
                name = "Medium";
                break;
            case 3:
                name = "High";
                break;
        }
        return name;
    }
}
