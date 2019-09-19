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

    @ColumnInfo(name = "list_of_session")
    private List<Session> mListOfSessions;

    @ColumnInfo(name = "note")
    private String mNote;

    @ColumnInfo(name = "checked")
    private boolean mChecked;

    // Empty constructor
    public CompletedExerciseItem() {
    }

    // Constructor for completed exercises
    public CompletedExerciseItem(ExerciseType type, String exerciseName, Date exerciseDate, List<Session> listOfSessions, String note) {
        mExerciseType = type;
        mExerciseName = exerciseName;
        mExerciseDate= exerciseDate;
        mListOfSessions = listOfSessions;
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

    public void setListOfSessions(List<Session> listOfSession) {
        mListOfSessions = listOfSession;
    }

    public List<Session> getListOfSessions() {
        return mListOfSessions;
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
    public boolean compareListOfSessions(List<Session> anotherListOfSessions) {
        if (mListOfSessions.size() != anotherListOfSessions.size()) {
            return false;
        }
        for (int i = 0; i< mListOfSessions.size(); i++) {
            Session thisSession = mListOfSessions.get(i);
            Session thatSession = anotherListOfSessions.get(i);
            if (thisSession.getType() != thatSession.getType()) {
                // Throw exception --- to do.
                return false;
            } else {
                switch (thisSession.getType()) {
                    case CALISTHENICS:
                    case STRENGTH:
                        if (thisSession.getReps() != thatSession.getReps() && thisSession.getWeight() != thatSession.getWeight()) {
                            return false;
                        }
                        break;
                    case CARDIO:
                        if (thisSession.getDuration() != thatSession.getDuration() && thisSession.getIntensity() != thatSession.getIntensity()) {
                            return false;
                        }
                        break;
                }
            }
        }
        return true;
    }
}
