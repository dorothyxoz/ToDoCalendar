package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.Handler;

import com.example.myapplication.fragment.CalendarFragment;
import com.example.myapplication.fragment.LoadingFragment;
import com.example.myapplication.fragment.ToDoListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private static final int LOADING_TIME = 2000; // 2 seconds
    private LoadingFragment loadingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set ActionBar title to "calendar"
        getSupportActionBar().setTitle("Todo Calendar");


        // Show loading screen
        showLoadingScreen();

        // Simulate some delay for loading
        new Handler().postDelayed(() -> {
            // Replace the loading screen with the initial fragment (e.g., ToDoListFragment)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new CalendarFragment())
                    .commit();

            // Hide the loading screen
            hideLoadingScreen();

            // Set ActionBar title to "calendar"
            //getSupportActionBar().setTitle("Todo Calendar");
        }, LOADING_TIME);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ToDoListFragment()).commit();
    }

    private void showLoadingScreen() {
        loadingFragment = new LoadingFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, loadingFragment)
                .commit();
    }

    private void hideLoadingScreen() {
        if (loadingFragment != null) {
            getSupportFragmentManager().beginTransaction().remove(loadingFragment).commit();
            loadingFragment = null;
        }
    }



    private NavigationBarView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.action_todo:
                        selectedFragment = new ToDoListFragment();
                        break;
                    case R.id.action_calendar:
                        selectedFragment = new CalendarFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selectedFragment).commit();
                return true;
            };
}
