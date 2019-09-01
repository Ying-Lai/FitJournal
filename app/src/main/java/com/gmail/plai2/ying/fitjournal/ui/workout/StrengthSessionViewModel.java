package com.gmail.plai2.ying.fitjournal.ui.workout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StrengthSessionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StrengthSessionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Work in progress.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}