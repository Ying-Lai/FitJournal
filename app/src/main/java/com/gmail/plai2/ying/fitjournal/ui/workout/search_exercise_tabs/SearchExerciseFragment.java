package com.gmail.plai2.ying.fitjournal.ui.workout.search_exercise_tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.gmail.plai2.ying.fitjournal.MainActivity;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

public class SearchExerciseFragment extends Fragment {

    // Static fields
    private static final String TAG = "SearchExerciseFragment";

    // Input fields
    private LocalDate mCurrentDateInput;
    private ExerciseType mExerciseTypeInput;


    // UI fields
    private CustomViewPager mViewpager;
    private TabLayout mTabLayout;

    // Empty constructor
    public SearchExerciseFragment() {
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
            ArrayList<String> exerciseInfo = getArguments().getStringArrayList(MainActivity.EXERCISE_INFO);
            mCurrentDateInput = TypeConverters.stringToDate(getArguments().getString(MainActivity.DATE_INFO));
            if (exerciseInfo != null) mExerciseTypeInput = TypeConverters.intToExerciseType(Integer.parseInt(exerciseInfo.get(0)));
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Initialize fields and variables
        View root = inflater.inflate(R.layout.fragment_search_exercise, container, false);
        MaterialToolbar toolbar = root.findViewById(R.id.search_exercise_tb);
        mViewpager = root.findViewById(R.id.search_exercise_vp);
        mTabLayout = root.findViewById(R.id.search_exercise_tabs);

        // Setup app tool bar
        if (getActivity() != null) {
            ((MainActivity)getActivity()).setSupportActionBar(toolbar);
            ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(mExerciseTypeInput.getCategoryName());
            } else {
                Log.e(TAG, "onCreateView: Could not get reference to support action bar");
            }
        } else {
            Log.e(TAG, "onCreateView: Could not get reference to activity");
        }

        // Setup viewpager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(FavoriteFragment.newInstance(mCurrentDateInput, mExerciseTypeInput), "Favorite");
        adapter.addFragment(CustomFragment.newInstance(mCurrentDateInput, mExerciseTypeInput), "Custom");
        adapter.addFragment(BrowseFragment.newInstance(mCurrentDateInput, mExerciseTypeInput), "Browse");
        mViewpager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewpager);
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
            if (getActivity() != null) {
                ((MainActivity)getActivity()).getNavController().popBackStack();
            } else {
                Log.e(TAG, "onOptionsItemSelected: Could not get reference to activity");
            }
        }
        return true;
    }

    // Other methods
    TabLayout getTabLayout() {
        return mTabLayout;
    }

    CustomViewPager getViewPager() {
        return mViewpager;
    }
}
