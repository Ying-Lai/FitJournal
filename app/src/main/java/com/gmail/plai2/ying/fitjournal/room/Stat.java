package com.gmail.plai2.ying.fitjournal.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.threeten.bp.LocalDate;

@Entity(tableName = "stat_table")
public class Stat {

    // Fields
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "stat_date")
    private LocalDate mDate = LocalDate.now();

    @ColumnInfo(name = "weight")
    private int mWeight;

    @ColumnInfo(name = "body_fat")
    private int mBodyFat;

    // Empty constructor
    public Stat() {
    }

    // General constructor
    public Stat(@NonNull LocalDate date, int weight, int fat) {
        mDate = date;
        mWeight = weight;
        mBodyFat = fat;
    }

    // Getters and setters
    public org.threeten.bp.LocalDate getMDate() {
        return mDate;
    }

    public void setMDate(LocalDate mStatDate) {
        this.mDate = mStatDate;
    }

    public int getMWeight() {
        return mWeight;
    }

    public void setMWeight(int mWeight) {
        this.mWeight = mWeight;
    }

    public int getMBodyFat() {
        return mBodyFat;
    }

    public void setMBodyFat(int mBodyFat) {
        this.mBodyFat = mBodyFat;
    }

}
