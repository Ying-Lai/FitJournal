package com.gmail.plai2.ying.fitjournal;

import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavArgument;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Static fields
    public static final String EXERCISE_INFO = "exercise_info_key";
    public static final String STAT_INFO = "stat_info_key";
    public static final String DATE_INFO = "date_info_key";
    public static final int EMPTY = -1;

    // Fab state fields
    private boolean mFabVisible;
    private boolean mFabExpanded;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;

    // UI fields
    private FloatingActionButton mAddFAB;
    private FloatingActionButton mCalisthenicFAB;
    private FloatingActionButton mStrengthFAB;
    private FloatingActionButton mCardioFAB;
    private BottomNavigationView mBottomNav;

    // Navigation fields
    private NavController mNavController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidThreeTen.init(this);
        // Initialize fields and variables
        mBottomNav = findViewById(R.id.nav_view);
        mAddFAB = findViewById(R.id.workout_add_fab);
        mCalisthenicFAB = findViewById(R.id.calisthenics_session_fab);
        mStrengthFAB = findViewById(R.id.strength_session_fab);
        mCardioFAB = findViewById(R.id.cardio_session_fab);

        // Animations
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clockwise);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlockwise);

        // Close fab submenus initially
        closeSubMenusFab();

        // Setup bottom navigation
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_to_workout_today, R.id.navigation_to_stats, R.id.navigation_to_calendar)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBottomNav, mNavController);
        mNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_to_workout_today || destination.getId() == R.id.navigation_to_workout_another_day) {
                    showFloatingActionButton();
                } else {
                    if (mFabVisible) hideFloatingActionButton();
                }
                if (destination.getId() != R.id.navigation_to_workout_today && destination.getId() != R.id.navigation_to_stats && destination.getId()
                        != R.id.navigation_to_calendar && destination.getId() != R.id.navigation_to_workout_another_day) {
                    hideBottomNavigationView();
                } else {
                    showBottomNavigationView();
                }
            }
        });

        // On click listeners
        mAddFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFabExpanded) {
                    mAddFAB.startAnimation(fab_anticlock);
                    closeSubMenusFab();
                } else {
                    mAddFAB.startAnimation(fab_clock);
                    openSubMenusFab();
                }
            }
        });
        mStrengthFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<String> exerciseInfo = new ArrayList<>();
                exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypeToInt(ExerciseType.STRENGTH)));
                bundle.putStringArrayList(EXERCISE_INFO, exerciseInfo);
                if (mNavController.getCurrentDestination().getId() == R.id.navigation_to_workout_another_day) {
                    NavArgument dateArgument = mNavController.getCurrentDestination().getArguments().get(DATE_INFO);
                    String dateInfo = (String) dateArgument.getDefaultValue();
                    bundle.putString(DATE_INFO, dateInfo);
                    mNavController.navigate(R.id.to_search_exercise, bundle);
                } else if (mNavController.getCurrentDestination().getId() == R.id.navigation_to_workout_today) {
                    String dateInfo = TypeConverters.dateToString(LocalDate.now()) ;
                    bundle.putString(DATE_INFO, dateInfo);
                    mNavController.navigate(R.id.to_search_exercise, bundle);
                }
            }
        });
        mCardioFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<String> exerciseInfo = new ArrayList<>();
                exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypeToInt(ExerciseType.CARDIO)));
                bundle.putStringArrayList(EXERCISE_INFO, exerciseInfo);
                if (mNavController.getCurrentDestination().getId() == R.id.navigation_to_workout_another_day) {
                    NavArgument dateArgument = mNavController.getCurrentDestination().getArguments().get(DATE_INFO);
                    String dateInfo = (String) dateArgument.getDefaultValue();
                    bundle.putString(DATE_INFO, dateInfo);
                    mNavController.navigate(R.id.to_search_exercise, bundle);
                } else if (mNavController.getCurrentDestination().getId() == R.id.navigation_to_workout_today) {
                    String dateInfo = TypeConverters.dateToString(LocalDate.now()) ;
                    bundle.putString(DATE_INFO, dateInfo);
                    mNavController.navigate(R.id.to_search_exercise, bundle);
                }
            }
        });
        mCalisthenicFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<String> exerciseInfo = new ArrayList<>();
                exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypeToInt(ExerciseType.CALISTHENICS)));
                bundle.putStringArrayList(EXERCISE_INFO, exerciseInfo);
                if (mNavController.getCurrentDestination().getId() == R.id.navigation_to_workout_another_day) {
                    NavArgument dateArgument = mNavController.getCurrentDestination().getArguments().get(DATE_INFO);
                    String dateInfo = (String) dateArgument.getDefaultValue();
                    bundle.putString(DATE_INFO, dateInfo);
                    mNavController.navigate(R.id.to_search_exercise, bundle);
                } else if (mNavController.getCurrentDestination().getId() == R.id.navigation_to_workout_today) {
                    String dateInfo = TypeConverters.dateToString(LocalDate.now()) ;
                    bundle.putString(DATE_INFO, dateInfo);
                    mNavController.navigate(R.id.to_search_exercise, bundle);
                }
            }
        });
    }

    // Other main activity methods

    public void showFloatingActionButton() {
        if (mAddFAB != null) {
            mAddFAB.startAnimation(fab_anticlock);
            mAddFAB.setClickable(true);
            mAddFAB.show();
            mFabVisible = true;
        }
    }

    public void hideFloatingActionButton() {
        if (mAddFAB != null) {
            mAddFAB.startAnimation(fab_clock);
            if (mFabExpanded) closeSubMenusFab();
            mAddFAB.hide();
            mAddFAB.setClickable(false);
            mFabVisible = false;
        }
    }

    public void hideBottomNavigationView() {
        mBottomNav.clearAnimation();
        mBottomNav.animate().translationY(mBottomNav.getHeight()).setDuration(300);
    }

    public void showBottomNavigationView() {
        mBottomNav.clearAnimation();
        mBottomNav.animate().translationY(0).setDuration(300);
    }

    public NavController getNavController() {
        return mNavController;
    }

    // Closes FAB submenus
    private void closeSubMenusFab(){
        mCalisthenicFAB.startAnimation(fab_close);
        mCardioFAB.startAnimation(fab_close);
        mStrengthFAB.startAnimation(fab_close);
        mCalisthenicFAB.setVisibility(View.GONE);
        mCardioFAB.setVisibility(View.GONE);
        mStrengthFAB.setVisibility(View.GONE);
        mCalisthenicFAB.setClickable(false);
        mCardioFAB.setClickable(false);
        mStrengthFAB.setClickable(false);
        mFabExpanded = false;
    }

    // Opens FAB submenus
    private void openSubMenusFab(){
        mCalisthenicFAB.startAnimation(fab_open);
        mCardioFAB.startAnimation(fab_open);
        mStrengthFAB.startAnimation(fab_open);
        mCalisthenicFAB.setVisibility(View.VISIBLE);
        mCardioFAB.setVisibility(View.VISIBLE);
        mStrengthFAB.setVisibility(View.VISIBLE);
        mCalisthenicFAB.setClickable(true);
        mCardioFAB.setClickable(true);
        mStrengthFAB.setClickable(true);
        mFabExpanded = true;
    }

    // Close soft keyboard
    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

    // Open soft keyboard
    public void showKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } else {
            toggleKeyboard();
        }
    }

    public void toggleKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }
}
