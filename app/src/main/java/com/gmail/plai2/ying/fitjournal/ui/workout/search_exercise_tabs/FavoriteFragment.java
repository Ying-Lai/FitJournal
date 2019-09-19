package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.AvailableExerciseItem;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteFragment extends Fragment {

    // Input fields
    private ExerciseType mExerciseTypeInput;

    // UI fields
    private WorkoutViewModel mViewModel;
    private AvailableExerciseAdapter mAdapter;
    private TextView mFavoriteInstructionsTV;
    private RecyclerView mAvailableExerciseRV;

    // Empty Constructor
    public FavoriteFragment() {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
    }

    // New instance constructor
    public static FavoriteFragment newInstance(ExerciseType exerciseTypeInput) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle bundle = new Bundle();
        ArrayList<String> exerciseInfo = new ArrayList<>();
        exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(exerciseTypeInput)));
        bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // To enable menu for this fragment
        setHasOptionsMenu(true);
        // Parse through bundle
        if (getArguments() != null) {
            ArrayList<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
            mExerciseTypeInput = TypeConverters.intToExerciseType(Integer.parseInt(exerciseInfo.get(0)));
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        mAvailableExerciseRV = root.findViewById(R.id.favorite_exercise_list_rv);
        mFavoriteInstructionsTV = root.findViewById(R.id.favorite_instructions_tv);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Setup adaptor
        mAvailableExerciseRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mAvailableExerciseRV.setHasFixedSize(true);
        mAdapter = new AvailableExerciseAdapter(Collections.emptyList(), new AvailableExerciseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                AvailableExerciseItem currentAvailableExercise = mAdapter.getExerciseItem(position);
                if (view.getId() == R.id.available_exercise_name_tv) {
                    Bundle bundle = new Bundle();
                    ArrayList<String> exerciseInfo = new ArrayList<>();
                    exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(mExerciseTypeInput)));
                    exerciseInfo.add(currentAvailableExercise.getExerciseName());
                    bundle.putStringArrayList(MainActivity.EXERCISE_INFO, exerciseInfo);
                    if (Navigation.findNavController(view).getCurrentDestination().getId() == R.id.navigation_search_exercise) {
                        Navigation.findNavController(view).navigate(R.id.to_session, bundle);
                    }
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

        // Observe live data
        mViewModel.getAllAvailableFavoritedExercise(true, mExerciseTypeInput).observe(getViewLifecycleOwner(), new Observer<List<AvailableExerciseItem>>() {
            @Override
            public void onChanged(List<AvailableExerciseItem> availableFavoritedExercises) {
                mAdapter.setAvailableExerciseItems(availableFavoritedExercises);
                if (availableFavoritedExercises.size() != 0) {
                    mFavoriteInstructionsTV.setVisibility(View.INVISIBLE);
                } else {
                    mFavoriteInstructionsTV.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Setup menu options
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_exercise_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search_menu_item);
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
