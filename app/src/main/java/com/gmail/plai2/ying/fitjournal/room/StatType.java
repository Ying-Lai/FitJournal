package com.gmail.plai2.ying.fitjournal.room;

public enum StatType {
    WEIGHT(0),
    BODYFAT(1);


    // Fields
    private int mCategory;

    // Constructor
    StatType(int category) {
        this.mCategory = category;
    }

    // Getter
    public int getCategory() {
        return mCategory;
    }

    // Other methods
    public String getCategoryName() {
        String name = null;
        switch(mCategory) {
            case 0:
                name = "Weight";
                break;
            case 1:
                name = "Stat";
                break;
        }
        return name;
    }
}