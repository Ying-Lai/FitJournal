package com.gmail.plai2.ying.fitjournal.ui.workout.browse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BrowseFragment extends Fragment {

    private static final String EXERCISE_TYPE = "exercise_type_key";
    private String exerciseTypeInput;
    private WorkoutViewModel viewModel;
    private FloatingActionButton placeholderFAB;

    public BrowseFragment() {
        // Required empty public constructor
    }

    public static BrowseFragment newInstance(String exerciseTypeInput) {
        BrowseFragment fragment = new BrowseFragment();
        Bundle args = new Bundle();
        args.putString(EXERCISE_TYPE, exerciseTypeInput);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.exerciseTypeInput = getArguments().getString(EXERCISE_TYPE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_browse, container, false);
        placeholderFAB = root.findViewById(R.id.placeholder_fab);
        placeholderFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exerciseTypeInput == "Cardio") {
                    Bundle bundle = new Bundle();
                    bundle.putString("exercise_type_key", "Cardio");
                    bundle.putString("exercise_name_key", "Jogging");
                    Navigation.findNavController(view).navigate(R.id.to_cardio_session, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("exercise_type_key", "Strength");
                    bundle.putString("exercise_name_key", "Chest Press");
                    Navigation.findNavController(view).navigate(R.id.to_strength_session, bundle);
                }
            }
        });
        return root;
    }
}