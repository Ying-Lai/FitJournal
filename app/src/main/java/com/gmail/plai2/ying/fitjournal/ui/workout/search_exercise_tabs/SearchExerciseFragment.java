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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import com.gmail.plai2.ying.fitjournal.R;
import com.gmail.plai2.ying.fitjournal.ui.workout.ViewPagerAdapter;
import com.gmail.plai2.ying.fitjournal.ui.workout.WorkoutViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

public class SearchExerciseFragment extends Fragment {

    private static final String EXERCISE_TYPE = "exercise_type_key";
    private WorkoutViewModel mViewModel;
    private String mExerciseTypeInput;

    public SearchExerciseFragment() {
        setHasOptionsMenu(true);
    }

    public static SearchExerciseFragment newInstance(String exerciseTypeInput) {
        SearchExerciseFragment fragment = new SearchExerciseFragment();
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
        View root = inflater.inflate(R.layout.fragment_search_exercise, container, false);
        MaterialToolbar toolbar = root.findViewById(R.id.search_exercise_tb);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mExerciseTypeInput);
        ViewPager viewPager = root.findViewById(R.id.search_exercise_vp);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getContext(), getChildFragmentManager());
        adapter.addFragment(FavoriteFragment.newInstance(mExerciseTypeInput), "Favorite");
        adapter.addFragment(CustomFragment.newInstance(mExerciseTypeInput), "Custom");
        adapter.addFragment(BrowseFragment.newInstance(mExerciseTypeInput), "Browse");
        viewPager.setAdapter(adapter);
        TabLayout tabs = root.findViewById(R.id.search_exercise_tabs);
        tabs.setupWithViewPager(viewPager);
        Toast.makeText(getActivity(), adapter.getCount() + "", Toast.LENGTH_SHORT).show();
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_exercise_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // need to check backstack first
            case android.R.id.home:
                Navigation.findNavController(getView()).popBackStack();
                break;
        }
        return true;
    }
}
