package com.gmail.plai2.ying.fitjournal;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton strengthFab;
    private FloatingActionButton cardioFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_workout, R.id.navigation_stats, R.id.navigation_gallery, R.id.navigation_calendar)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        strengthFab = findViewById(R.id.strength_fab);
        cardioFab= findViewById(R.id.cardio_fab);
        strengthFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_to_strength_session);
            }
        });
        cardioFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_to_cardio_session);
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if(destination.getId() == R.id.navigation_workout) {
                    showFloatingActionButton();
                } else {
                    hideFloatingActionButton();
                }
            }
        });

    }

    public void showFloatingActionButton() {
        if (cardioFab != null && strengthFab != null) {
            cardioFab.show();
            strengthFab.show();
        }
    }

    public void hideFloatingActionButton() {
        if (cardioFab != null && strengthFab != null) {
            cardioFab.hide();
            strengthFab.hide();
        }
    }
}
