package com.gmail.plai2.ying.fitjournal.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "completed_exercise_table")
public class CompletedExerciseItem {

    // Fields
    @PrimaryKey(autoGenerate = true)
    private int mId; // Look into this later -> Can't find getter for mId

    @ColumnInfo(name = "exercise_type")
    private ExerciseType mExerciseType;

    @ColumnInfo(name = "exercise_name")
    private String mExerciseName;

    @ColumnInfo(name = "exercise_date")
    private Date mExerciseDate;

    @ColumnInfo(name = "list_of_sets")
    private List<Set> mListOfSets;

    @ColumnInfo(name = "list_of_cardio_sessions")
    private List<CardioSession> mListOfCardioSessions;

    @ColumnInfo(name = "note")
    private String mNote;

    @ColumnInfo(name = "checked")
    private boolean mChecked;

    // Empty constructor
    public CompletedExerciseItem() {
    }

    // Constructor for cardio exercises
    public CompletedExerciseItem(ExerciseType type, String exerciseName, Date exerciseDate, String note, List<CardioSession> listOfCardioSessions) {
        mExerciseType = type;
        mExerciseName = exerciseName;
        mExerciseDate= exerciseDate;
        mListOfCardioSessions = listOfCardioSessions;
        mNote = note;
        mChecked = false;
    }

    // Constructor for strength exercises
    public CompletedExerciseItem(ExerciseType type, String exerciseName, Date exerciseDate, List<Set> listOfSets, String note) {
        mExerciseType = type;
        mExerciseName = exerciseName;
        mExerciseDate= exerciseDate;
        mListOfSets = listOfSets;
        mNote = note;
        mChecked = false;
    }

    // Getters and setters for fields
    public void setMId(int id) {
        mId = id;
    }

    public int getMId() {
        return mId;
    }

    public ExerciseType getExerciseType() {
        return mExerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        mExerciseType = exerciseType;
    }

    public void setExerciseName(String exerciseName) {
        mExerciseName = exerciseName;
    }

    public String getExerciseName() {
        return mExerciseName;
    }

    public Date getExerciseDate() {
        return mExerciseDate;
    }

    public void setExerciseDate(Date exerciseDate) {
        mExerciseDate = exerciseDate;
    }

    public void setListOfSets(List<Set> listOfSets) {
        mListOfSets = listOfSets;
    }

    public List<Set> getListOfSets() {
        return mListOfSets;
    }

    public void setListOfCardioSessions(List<CardioSession> listOfCardioSessions) {
        mListOfCardioSessions = listOfCardioSessions;
    }

    public List<CardioSession> getListOfCardioSessions() {
        return mListOfCardioSessions;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getNote() { return mNote; }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public boolean isChecked() {
        return mChecked;
    }

    // Other methods
    public int getMinRep(List<Set> listOfSets) {
        int min = listOfSets.get(0).getReps();
        for (int i=0; i<listOfSets.size(); i++) {
            if (listOfSets.get(i).getReps() < min) {
                min = listOfSets.get(i).getReps();
            }
        }
        return min;
    }

    public int getMaxRep(List<Set> listOfSets) {
        int max = listOfSets.get(0).getReps();
        for (int i=0; i<listOfSets.size(); i++) {
            if (listOfSets.get(i).getReps() > max) {
                max = listOfSets.get(i).getReps();
            }
        }
        return max;
    }

    public boolean compareSet(List<Set> anotherSet) {
        if (mListOfSets.size() != anotherSet.size()) {
            return false;
        }
        for (int i=0; i<mListOfSets.size(); i++) {
            if (mListOfSets.get(i).getReps() != anotherSet.get(i).getReps()
                    && mListOfSets.get(i).getWeight() != anotherSet.get(i).getWeight()) {
                return false;
            }
        }
        return true;
    }

    public boolean compareSession(List<CardioSession> anotherSession) {
        if (mListOfSets.size() != anotherSession.size()) {
            return false;
        }
        for (int i=0; i<mListOfSets.size(); i++) {
            if (mListOfCardioSessions.get(i).getIntensity() != anotherSession.get(i).getIntensity()
                    && mListOfCardioSessions.get(i).getDuration() != anotherSession.get(i).getDuration()) {
                return false;
            }
        }
        return true;
    }
}
