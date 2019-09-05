package com.gmail.plai2.ying.fitjournal;

import android.os.Bundle;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

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

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton strengthFAB;
    private FloatingActionButton cardioFAB;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_to_workout, R.id.navigation_to_stats, R.id.navigation_to_gallery, R.id.navigation_to_calendar)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        bottomNav = findViewById(R.id.nav_view);
        strengthFAB = findViewById(R.id.strength_session_fab);
        cardioFAB = findViewById(R.id.cardio_session_fab);
        strengthFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("exercise_type_key", "Strength");
                navController.navigate(R.id.to_search_exercise, bundle);
            }
        });
        cardioFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("exercise_type_key", "Cardio");
                navController.navigate(R.id.to_search_exercise, bundle);
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_to_workout) {
                    showFloatingActionButton();
                } else {
                    hideFloatingActionButton();
                }
                if (destination.getId() != R.id.navigation_to_workout && destination.getId() != R.id.navigation_to_stats && destination.getId() != R.id.navigation_to_gallery && destination.getId() != R.id.navigation_to_calendar) {
                    hideBottomNavigationView(bottomNav);
                } else {
                    showBottomNavigationView(bottomNav);
                }
            }
        });

    }

    public void showFloatingActionButton() {
        if (cardioFAB != null && strengthFAB != null) {
            cardioFAB.show();
            strengthFAB.show();
        }
    }

    public void hideFloatingActionButton() {
        if (cardioFAB != null && strengthFAB != null) {
            cardioFAB.hide();
            strengthFAB.hide();
        }
    }

    private void hideBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(view.getHeight()).setDuration(300);
    }

    public void showBottomNavigationView(BottomNavigationView view) {
        view.clearAnimation();
        view.animate().translationY(0).setDuration(300);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_exercise_menu, menu);
        return true;
    }
}
