package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SearchExerciseFragment extends Fragment {

    // Input fields
    private ExerciseType mExerciseTypeInput;


    // UI fields
    private WorkoutViewModel mViewModel;
    private MaterialToolbar mToolbar;
    private CustomViewPager mViewpager;
    private TabLayout mTablayout;

    // Empty constructor
    public SearchExerciseFragment() {
        // To enable menu for this fragment
        setHasOptionsMenu(true);
    }

    // New instance constructor
    public static SearchExerciseFragment newInstance(ExerciseType exerciseTypeInput) {
        SearchExerciseFragment fragment = new SearchExerciseFragment();
        Bundle bundle = new Bundle();
        ArrayList<String> exerciseInfo = new ArrayList<>();
        exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypeToInt(exerciseTypeInput)));
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
        mViewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search_exercise, container, false);
        mToolbar = root.findViewById(R.id.search_exercise_tb);
        mViewpager = root.findViewById(R.id.search_exercise_vp);
        mTablayout = root.findViewById(R.id.search_exercise_tabs);

        // Setup app tool bar
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mExerciseTypeInput.getCategoryName());

        // Setup viewpager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), getChildFragmentManager());
        adapter.addFragment(FavoriteFragment.newInstance(mExerciseTypeInput), "Favorite");
        adapter.addFragment(CustomFragment.newInstance(mExerciseTypeInput), "Custom");
        adapter.addFragment(BrowseFragment.newInstance(mExerciseTypeInput), "Browse");
        mViewpager.setAdapter(adapter);
        mTablayout.setupWithViewPager(mViewpager);
        return root;
    }

    // Setup menu options
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_exercise_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(getView()).popBackStack();
        }
        return true;
    }

    // Other methods
    public TabLayout getTabLayout() {
        return mTablayout;
    }

    public CustomViewPager getViewPager() {
        return mViewpager;
    }
}
