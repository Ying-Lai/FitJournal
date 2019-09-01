package com.gmail.plai2.ying.fitjournal.ui.workout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CardioSessionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CardioSessionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Work in progress.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}