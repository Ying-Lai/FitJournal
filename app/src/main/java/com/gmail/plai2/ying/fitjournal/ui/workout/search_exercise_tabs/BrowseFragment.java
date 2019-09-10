package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;

import java.util.Collections;
import java.util.List;

public class BrowseFragment extends Fragment {

    private static final String EXERCISE_TYPE = "exercise_type_key";
    private static final String EXERCISE_NAME = "exercise_name_key";
    private String mExerciseTypeInput;
    private WorkoutViewModel mViewModel;
    private RecyclerView mAvailableExerciseRV;
    private AvailableExerciseAdapter mAdapter;

    public BrowseFragment() {
        setHasOptionsMenu(true);
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
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            this.mExerciseTypeInput = getArguments().getString(EXERCISE_TYPE);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_browse, container, false);
        mAvailableExerciseRV = root.findViewById(R.id.browse_exercise_list_rv);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(WorkoutViewModel.class);
        mAvailableExerciseRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mAvailableExerciseRV.setHasFixedSize(true);
        mAdapter = new AvailableExerciseAdapter(Collections.emptyList(), new AvailableExerciseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                AvailableExerciseItem currentAvailableExercise = mAdapter.getExerciseItem(position);
                if (view.getId() == R.id.available_exercise_name_tv) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EXERCISE_TYPE, mExerciseTypeInput);
                    bundle.putString(EXERCISE_NAME, currentAvailableExercise.getExerciseName());
                    Navigation.findNavController(view).navigate((mExerciseTypeInput.equals("Cardio")?R.id.to_cardio_session:R.id.to_strength_session), bundle);
                } else if (view.getId() == R.id.available_exercise_favorited_iv) {
                    if (currentAvailableExercise.isFavorited()) {
                        currentAvailableExercise.setFavorited(false);
                    } else {
                        currentAvailableExercise.setFavorited(true);
                    }
                    mViewModel.update(currentAvailableExercise);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mAvailableExerciseRV.setAdapter(mAdapter);
        AvailableExerciseItem.ExerciseType type = (mExerciseTypeInput == "Cardio") ? AvailableExerciseItem.ExerciseType.CARDIO : AvailableExerciseItem.ExerciseType.STRENGTH;
        mViewModel.getAllAvailableExercises(type).observe(getViewLifecycleOwner(), new Observer<List<AvailableExerciseItem>>() {
            @Override
            public void onChanged(List<AvailableExerciseItem> availableExerciseItems) {
                mAdapter.setAvailableExerciseItems(availableExerciseItems);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_exercise_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_button);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
