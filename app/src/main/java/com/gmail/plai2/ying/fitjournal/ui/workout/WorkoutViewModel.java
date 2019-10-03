package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.repository.ExerciseRepository;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;

import org.threeten.bp.LocalDate;

import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {

    // Fields
    private ExerciseRepository mRepository;
    private MutableLiveData<CompletedExerciseItem> mExerciseInput = new MutableLiveData<>();
    private LiveData<List<CompletedExerciseItem>> mAllCompletedExercises;

    // Constructor
    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ExerciseRepository(application);
        mAllCompletedExercises = mRepository.getAllCompletedExercises();
    }

    // Methods for Available Exercises Item
    public void insert(AvailableExerciseItem availableExerciseItem) {
        mRepository.insert(availableExerciseItem);
    }

    public void update(AvailableExerciseItem availableExerciseItem) {
        mRepository.update(availableExerciseItem);
    }

    public void delete(AvailableExerciseItem availableExerciseItem) {
        mRepository.delete(availableExerciseItem);
    }

    public void deleteAllAvailableExercise() { mRepository.deleteAllAvailableExercises();}

    public LiveData<List<AvailableExerciseItem>> getAllCustomAvailableExercise(boolean custom, ExerciseType exerciseType) {
        return mRepository.getAllCustomAvailableExercise(custom, exerciseType);
    }

    public LiveData<List<AvailableExerciseItem>> getAllAvailableFavoritedExercise(boolean favorited, ExerciseType exerciseType) {
        return mRepository.getAllAvailableFavoriteExercise(favorited, exerciseType);
    }

    public LiveData<List<AvailableExerciseItem>> getAllAvailableExercises(ExerciseType exerciseType) {
        return mRepository.getAllAvailableExercises(exerciseType);
    }

    // Methods for Completed Exercises Item
    public void setExerciseInput(CompletedExerciseItem completedExerciseItem) {
        mExerciseInput.setValue(completedExerciseItem);
    }

    public LiveData<CompletedExerciseItem> getExerciseInput() {
        return mExerciseInput;
    }

    public void insert(CompletedExerciseItem completedExerciseItem) {
        mRepository.insert(completedExerciseItem);
    }

    public void update(CompletedExerciseItem completedExerciseItem) {
        mRepository.update(completedExerciseItem);
    }

    public void delete(CompletedExerciseItem completedExerciseItem) {
        mRepository.delete(completedExerciseItem);
    }

    public void deleteAllCompletedExerciseByDate(LocalDate date) {
        mRepository.deleteAllCompletedExerciseByDate(date); }

    public void deleteAllCompletedExercise() { mRepository.deleteAllCompletedExercises();}

    public LiveData<List<CompletedExerciseItem>> getAllCompletedExercisesByDate(LocalDate date) {
        return mRepository.getAllCompletedExerciseByDate(date);
    }

    public LiveData<List<CompletedExerciseItem>> getAllCompletedExercises() {
        return mAllCompletedExercises;
    }
}