package com.gmail.plai2.ying.fitjournal.ui.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gmail.plai2.ying.fitjournal.R;

public class CardioSessionFragment extends Fragment {

    private CardioSessionViewModel cardioSessionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardioSessionViewModel = ViewModelProviders.of(this).get(CardioSessionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cardio_session, container, false);
        return root;
    }
}