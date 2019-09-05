package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gmail.plai2.ying.fitjournal.room.Exercise;
import com.gmail.plai2.ying.fitjournal.repository.ExerciseRepository;

import java.util.Date;
import java.util.List;

public class WorkoutViewModel extends AndroidViewModel {

    private ExerciseRepository repository;
    private MutableLiveData<Exercise> exerciseInput = new MutableLiveData<>();
    private LiveData<List<Exercise>> allExercisesByDate;
    private LiveData<List<Exercise>> allExercises;

    public WorkoutViewModel(@NonNull Application application) {
        super(application);
        repository = new ExerciseRepository(application);
        allExercises = repository.getAllExercises();
    }

    public void setExerciseInput(Exercise exercise) {
        exerciseInput.setValue(exercise);
    }

    public LiveData<Exercise> getExerciseInput() {
        return exerciseInput;
    }

    public void insert(Exercise exercise) {
        repository.insert(exercise);
    }

    public void update(Exercise exercise) {
        repository.update(exercise);
    }

    public void delete(Exercise exercise) {
        repository.delete(exercise);
    }

    public void deleteAllByDate(Date date) {repository.deleteAllExerciseByDate(date); }

    public void deleteAll() { repository.deleteAllExercises();}

    public LiveData<List<Exercise>> getAllExercisesByDate(Date date) {
        return repository.getExerciseByDate(date);
    }

    public LiveData<List<Exercise>> getAllExercises() {
        return allExercises;
    }
}