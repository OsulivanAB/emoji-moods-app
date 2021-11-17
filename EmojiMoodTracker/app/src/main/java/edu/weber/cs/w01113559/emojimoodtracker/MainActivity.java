package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import edu.weber.cs.w01113559.emojimoodtracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View Binding
        edu.weber.cs.w01113559.emojimoodtracker.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        // Initialize Data
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkIfUserIsLoggedIn();
    }

    private void initData() {
        auth = FirebaseAuth.getInstance();
    }

    /**
     * Checks if the user is logged in.
     * If they are not, it directs them to the login activity.
     */
    private void checkIfUserIsLoggedIn(){
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, DashboardActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        finish();
    }
}