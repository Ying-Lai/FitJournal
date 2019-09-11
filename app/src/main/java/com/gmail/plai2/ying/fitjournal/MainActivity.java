package com.gmail.plai2.ying.fitjournal;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

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

    // UI fields
    private FloatingActionButton mStrengthFAB;
    private FloatingActionButton mCardioFAB;
    private BottomNavigationView mBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize fields and variables
        mBottomNav = findViewById(R.id.nav_view);
        mStrengthFAB = findViewById(R.id.strength_session_fab);
        mCardioFAB = findViewById(R.id.cardio_session_fab);

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
        mStrengthFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<String> exerciseInfo = new ArrayList<>();
                exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(ExerciseType.STRENGTH)));
                bundle.putStringArrayList(EXERCISE_INFO, exerciseInfo);
                navController.navigate(R.id.to_search_exercise, bundle);
            }
        });
        mCardioFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ArrayList<String> exerciseInfo = new ArrayList<>();
                exerciseInfo.add(Integer.toString(TypeConverters.exerciseTypetoInt(ExerciseType.CARDIO)));
                bundle.putStringArrayList(EXERCISE_INFO, exerciseInfo);
                navController.navigate(R.id.to_search_exercise, bundle);
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
        if (mCardioFAB != null && mStrengthFAB != null) {
            mCardioFAB.show();
            mStrengthFAB.show();
        }
    }

    public void hideFloatingActionButton() {
        if (mCardioFAB != null && mStrengthFAB != null) {
            mCardioFAB.hide();
            mStrengthFAB.hide();
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
}
