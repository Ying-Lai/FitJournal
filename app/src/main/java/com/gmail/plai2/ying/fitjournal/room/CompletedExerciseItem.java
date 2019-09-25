package com.gmail.plai2.ying.fitjournal.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
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
    private LocalDate mExerciseDate;

    @ColumnInfo(name = "list_of_session")
    private List<Session> mListOfSessions;

    @ColumnInfo(name = "note")
    private String mNote;

    @Ignore
    private boolean mIsChecked;

    // Empty constructor
    public CompletedExerciseItem() {
    }

    // Constructor for completed exercises
    public CompletedExerciseItem(ExerciseType type, String exerciseName, LocalDate exerciseDate, List<Session> listOfSessions, String note) {
        mExerciseType = type;
        mExerciseName = exerciseName;
        mExerciseDate= exerciseDate;
        mListOfSessions = listOfSessions;
        mNote = note;
        mIsChecked = false;
    }

    // Deep copy constructor
    public CompletedExerciseItem(CompletedExerciseItem anotherItem) {
        mId = anotherItem.getMId();
        mExerciseType = anotherItem.getMExerciseType();
        mExerciseName = anotherItem.getMExerciseName();
        mExerciseDate= anotherItem.getMExerciseDate();
        mListOfSessions = anotherItem.getMListOfSessions();
        mNote = anotherItem.getMNote();
        mIsChecked = anotherItem.isChecked();
    }

    // Getters and setters for fields
    public void setMId(int id) {
        mId = id;
    }

    public int getMId() {
        return mId;
    }

    public ExerciseType getMExerciseType() {
        return mExerciseType;
    }

    public void setMExerciseType(ExerciseType exerciseType) {
        mExerciseType = exerciseType;
    }

    public String getMExerciseName() {
        return mExerciseName;
    }

    public void setMExerciseName(String exerciseName) {
        mExerciseName = exerciseName;
    }


    public LocalDate getMExerciseDate() {
        return mExerciseDate;
    }

    public void setMExerciseDate(LocalDate exerciseDate) {
        mExerciseDate = exerciseDate;
    }

    public void setMListOfSessions(List<Session> listOfSession) {
        mListOfSessions = listOfSession;
    }

    public List<Session> getMListOfSessions() {
        return mListOfSessions;
    }

    public void setMNote(String note) {
        mNote = note;
    }

    public String getMNote() { return mNote; }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    public boolean isChecked() {
        return mIsChecked;
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
                        if (thisSession.getReps() != thatSession.getReps() || thisSession.getWeight() != thatSession.getWeight()) {
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
