package com.gmail.plai2.ying.fitjournal;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gmail.plai2.ying.fitjournal.room.ExerciseType;
import com.gmail.plai2.ying.fitjournal.room.TypeConverters;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Static fields
    public static final String EXERCISE_INFO = "exercise_info_key";

    // Fab state fields
    private boolean mFabExpanded;

    // UI fields
    private FloatingActionButton mAddFAB;
    private FloatingActionButton mCalisthenicFAB;
    private FloatingActionButton mStrengthFAB;
    private FloatingActionButton mCardioFAB;
    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize fields and variables
        mBottomNav = findViewById(R.id.nav_view);
        mAddFAB = findViewById(R.id.workout_add_fab);
        mCalisthenicFAB = findViewById(R.id.calisthenics_session_fab);
        mStrengthFAB = findViewById(R.id.strength_session_fab);
        mCardioFAB = findViewById(R.id.cardio_session_fab);

        // Close fab submenus initially
        closeSubMenusFab();

        // Setup bottom navigation
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_to_workout, R.id.navigation_to_stats, R.id.navigation_to_gallery, R.id.navigation_to_calendar)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(mBottomNav, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_to_workout) {
                    showFloatingActionButton();
                } else {
                    hideFloatingActionButton();
                }
                if (destination.getId() != R.id.navigation_to_workout && destination.getId() != R.id.navigation_to_stats && destination.getId() != R.id.navigation_to_gallery && destination.getId() != R.id.navigation_to_calendar) {
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
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });
        mStrengthFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<String> exerciseInfo = new ArrayList<>();
                exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(ExerciseType.STRENGTH)));
                bundle.putStringArrayList(EXERCISE_INFO, exerciseInfo);
                if (navController.getCurrentDestination().getId() == R.id.navigation_to_workout) {
                    navController.navigate(R.id.to_search_exercise, bundle);
                }
            }
        });
        mCardioFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<String> exerciseInfo = new ArrayList<>();
                exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(ExerciseType.CARDIO)));
                bundle.putStringArrayList(EXERCISE_INFO, exerciseInfo);
                if (navController.getCurrentDestination().getId() == R.id.navigation_to_workout) {
                    navController.navigate(R.id.to_search_exercise, bundle);
                }
            }
        });
        mCalisthenicFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<String> exerciseInfo = new ArrayList<>();
                exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(ExerciseType.CALISTHENICS)));
                bundle.putStringArrayList(EXERCISE_INFO, exerciseInfo);
                if (navController.getCurrentDestination().getId() == R.id.navigation_to_workout) {
                    navController.navigate(R.id.to_search_exercise, bundle);
                }
            }
        });
    }

    // Setup menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_exercise_menu, menu);
        return true;
    }

    // Other main activity methods
    public void showFloatingActionButton() {
        if (mAddFAB != null) {
            closeSubMenusFab();
            mAddFAB.show();
        }
    }

    public void hideFloatingActionButton() {
        if (mAddFAB != null) {
            closeSubMenusFab();
            mAddFAB.hide();
        }
    }

    private void hideBottomNavigationView() {
        mBottomNav.clearAnimation();
        mBottomNav.animate().translationY(mBottomNav.getHeight()).setDuration(300);
    }

    public void showBottomNavigationView() {
        mBottomNav.clearAnimation();
        mBottomNav.animate().translationY(0).setDuration(300);
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        mCalisthenicFAB.setVisibility(View.INVISIBLE);
        mCardioFAB.setVisibility(View.INVISIBLE);
        mStrengthFAB.setVisibility(View.INVISIBLE);
        mAddFAB.setImageResource(R.drawable.ic_add);
        mFabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        mCalisthenicFAB.setVisibility(View.VISIBLE);
        mCardioFAB.setVisibility(View.VISIBLE);
        mStrengthFAB.setVisibility(View.VISIBLE);
        mAddFAB.setImageResource(R.drawable.ic_close);
        mFabExpanded = true;
    }
}
