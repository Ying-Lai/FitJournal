package com.gmail.plai2.ying.fitjournal.ui.workout.calisthenics_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.Set;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.gmail.plai2.ying.fitjournal.ui.workout.strength_session.StrengthSetAdapter;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalisthenicsSessionFragment extends Fragment {

    // Input fields
    private ExerciseType mExerciseTypeInput;
    private String mExerciseNameInput;
    private int mExerciseIdInput;
    private List<Set> mExerciseSetInput;
    private boolean mShouldUpdate = false;

    // UI fields
    private WorkoutViewModel mViewModel;
    private RecyclerView mSetRV;
    private Button mNewSetButton;
    private Button mSaveButton;
    private MaterialToolbar mToolbar;

    public CalisthenicsSessionFragment() {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To enable menu for this fragment
        setHasOptionsMenu(true);
        // Parse through bundle
        if (getArguments() != null) {
            List<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
            mExerciseTypeInput = TypeConverters.intToExerciseType(Integer.parseInt(exerciseInfo.get(0)));
            mExerciseNameInput = exerciseInfo.get(1);
            if (exerciseInfo.size() > 2) {
                mExerciseIdInput = Integer.parseInt(exerciseInfo.get(2));
                mExerciseSetInput = TypeConverters.stringToSetList(exerciseInfo.get(3));
                mShouldUpdate = true;
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_calisthenics_session, container, false);
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        mToolbar = root.findViewById(R.id.calisthenics_session_tb);
        mNewSetButton = root.findViewById(R.id.new_calisthenics_set_btn);
        mSaveButton = root.findViewById(R.id.save_calisthenics_exercise_btn);
        mSetRV = root.findViewById(R.id.calisthenics_sets_rv);

        // Setup app tool bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mExerciseNameInput);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup adaptor
        mSetRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mSetRV.setHasFixedSize(true);
        final CalisthenicsSetAdapter adapter = new CalisthenicsSetAdapter();
        mSetRV.setAdapter(adapter);

        // Update elements if passed from workout fragment
        if (mShouldUpdate) {
            adapter.addListOfSets(mExerciseSetInput);
        } else {
            List<Set> initialSet = new ArrayList<>();
            initialSet.add(new Set(ExerciseType.CALISTHENICS));
            adapter.addListOfSets(initialSet);
        }

        // On click listeners
        mNewSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addIndividualSet(new Set(ExerciseType.CALISTHENICS));
            }
        });
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Set> newListOfSets = adapter.getSets();
                Date today = new Date();
                today.setTime(0);
                if (mShouldUpdate) {
                    // Change note here
                    CompletedExerciseItem updatedItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, today, newListOfSets, "");
                    updatedItem.setMId(mExerciseIdInput);
                    mViewModel.update(updatedItem);
                } else {
                    // Change note here
                    CompletedExerciseItem newItem = new CompletedExerciseItem(mExerciseTypeInput, mExerciseNameInput, today, newListOfSets, "");
                    mViewModel.insert(newItem);
                }
                Navigation.findNavController(view).popBackStack(R.id.navigation_to_workout, false);
            }
        });
    }

    // Setup menu options
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.exercise_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(getView()).popBackStack();
        }
        return true;
    }
}