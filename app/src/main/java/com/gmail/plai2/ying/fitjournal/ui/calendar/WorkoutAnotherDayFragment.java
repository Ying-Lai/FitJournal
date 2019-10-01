package com.gmail.plai2.ying.fitjournal.ui.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavArgument;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.room.CompletedExerciseItem;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.CompletedExerciseAdapter;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAnotherDayFragment extends Fragment {

    // Input fields
    private LocalDate mCurrentDateInput;

    // UI fields
    private RecyclerView mCompletedExerciseRV;
    private MaterialToolbar mToolbar;
    private CompletedExerciseAdapter mAdapter;
    private ImageView mWorkoutInstructionsIV;
    private MaterialTextView mWorkoutInstructionsTV;

    // Action mode fields
    private boolean mDeleteUsed;
    private ActionMode mActionMode;

    // View model fields
    private WorkoutViewModel mViewModel;

    public WorkoutAnotherDayFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To enable menu for this fragment
        setHasOptionsMenu(true);
        // Parse through bundle
        if (getArguments() != null) {
            mCurrentDateInput = TypeConverters.stringToDate(getArguments().getString(MainActivity.DATE_INFO));
        }
        NavArgument dateArgument = new NavArgument.Builder().setDefaultValue(TypeConverters.dateToString(mCurrentDateInput)).build();
        ((MainActivity)getActivity()).getNavController().getCurrentDestination().addArgument(MainActivity.DATE_INFO, dateArgument); // Reconsider Navigation.findNavController(view)
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_workout_another_day, container, false);
        mCompletedExerciseRV = root.findViewById(R.id.another_day_completed_exercise_rv);
        mToolbar = root.findViewById(R.id.workout_another_day_tb);
        mWorkoutInstructionsIV = root.findViewById(R.id.another_day_workout_instruction_iv);
        mWorkoutInstructionsTV = root.findViewById(R.id.another_day_workout_instruction_tv);

        // Setup app tool bar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String formattedCurrentDate = mCurrentDateInput.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM.MEDIUM));
        actionBar.setTitle(formattedCurrentDate);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup adaptor
        mCompletedExerciseRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mCompletedExerciseRV.setHasFixedSize(true);
        mAdapter = new CompletedExerciseAdapter(new CompletedExerciseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (mActionMode == null) {
                    CompletedExerciseItem currentCompletedExercise = mAdapter.getExerciseItem(position);
                    ArrayList<String> exerciseInfo = new ArrayList<>();
                    Bundle bundle = new Bundle();
                    String dateInfo = TypeConverters.dateToString(mCurrentDateInput);
                    bundle.putString(MainActivity.DATE_INFO, dateInfo);
                    switch (currentCompletedExercise.getMExerciseType()) {
                        case CALISTHENICS:
                            exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypeToInt(ExerciseType.CALISTHENICS)));
                            break;
                        case CARDIO:
                            exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypeToInt(ExerciseType.CARDIO)));
                            break;
                        case STRENGTH:
                            exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypeToInt(ExerciseType.STRENGTH)));
                            break;
                    }
                    exerciseInfo.add(currentCompletedExercise.getMExerciseName());
                    exerciseInfo.add(Integer.toString(currentCompletedExercise.getMId()));
                    exerciseInfo.add(TypeConverters.sessionListToString(currentCompletedExercise.getMListOfSessions()));
                    exerciseInfo.add(currentCompletedExercise.getMNote());
                    bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
                    if (Navigation.findNavController(view).getCurrentDestination().getId() == R.id.navigation_to_workout_another_day) {
                        Navigation.findNavController(view).navigate(R.id.to_session, bundle);
                    }
                }
            }

            @Override
            public boolean onLongClick(View view, int position) {
                if (mActionMode == null) {
                    mActionMode = ((MainActivity)getActivity()).startSupportActionMode(mActionModeCallBack);
                    mDeleteUsed = false;
                    ((MainActivity)getActivity()).hideFloatingActionButton();
                    ((MainActivity)getActivity()).hideBottomNavigationView();
                }
                CompletedExerciseItem currentExercise = mAdapter.getExerciseItem(position);
                List<CompletedExerciseItem> newList = new ArrayList<>();
                for (CompletedExerciseItem completedItem : mAdapter.getCurrentList()) {
                    newList.add(new CompletedExerciseItem(completedItem));
                }
                if (currentExercise.isChecked()) {
                    newList.get(position).setChecked(false);
                } else {
                    newList.get(position).setChecked(true);
                }
                mAdapter.submitList(newList);
                return true;
            }
        });
        mCompletedExerciseRV.setAdapter(mAdapter);

        // Observe live data
        mViewModel = ViewModelProviders.of(getActivity()).get(WorkoutViewModel.class);
        mViewModel.getAllCompletedExercisesByDate(mCurrentDateInput).observe(getViewLifecycleOwner(), new Observer<List<CompletedExerciseItem>>() {
            @Override
            public void onChanged(List<CompletedExerciseItem> completedExerciseItems) {
                if (completedExerciseItems.size() != 0) {
                    mWorkoutInstructionsIV.setVisibility(View.GONE);
                    mWorkoutInstructionsTV.setVisibility(View.GONE);
                } else {
                    mWorkoutInstructionsIV.setVisibility(View.VISIBLE);
                    mWorkoutInstructionsTV.setVisibility(View.VISIBLE);
                }
                mAdapter.submitList(completedExerciseItems);
            }
        });
    }

    // Setup action mode

    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.delete_menu, menu);
            mode.setTitle("Delete");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.delete_menu_item) {
                for (int i=0; i<mAdapter.getCurrentList().size(); i++) {
                    if (mAdapter.getCurrentList().get(i).isChecked()) {
                        mViewModel.delete(mAdapter.getCurrentList().get(i));
                    }
                }
                mDeleteUsed = true;
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            if (!mDeleteUsed) {
                List<CompletedExerciseItem> newList = new ArrayList<>();
                for (CompletedExerciseItem completedItem : mAdapter.getCurrentList()) {
                    newList.add(new CompletedExerciseItem(completedItem));
                }
                for (int i=0; i<newList.size(); i++) {
                    if (newList.get(i).isChecked()) {
                        newList.get(i).setChecked(false);
                    }
                }
                mAdapter.submitList(newList);
            }
            ((MainActivity)getActivity()).showFloatingActionButton();
            ((MainActivity)getActivity()).showBottomNavigationView();
        }
    };

    // Setup menu options
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.workout_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            ((MainActivity)getActivity()).getNavController().popBackStack();
        }
        return true;
    }
}