package edu.weber.cs.w01113559.emojimoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.User;
import edu.weber.cs.w01113559.emojimoodtracker.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ActivityRegisterBinding binding;
    private View root;
    private AppDatabase mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);
        initData();
    }

    private void initData(){
        auth = FirebaseAuth.getInstance();

        // Database
        mdatabase = new AppDatabase(this);

        // Register Button
        binding.btnRegister.setOnClickListener(v -> validateUser());

        // Sign in Button
        binding.llSignIn.setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), LoginActivity.class));
            finish();
        });
    }

    private void validateUser(){
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
        String cPassword = Objects.requireNonNull(binding.etcPassword.getText()).toString();

        if (!email.isEmpty() && !password.isEmpty() && !cPassword.isEmpty()){
            if(password.equals(cPassword)){
                createAccount(email, password);
            } else {
                Toast.makeText(this, "Password mismatch", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "All inputs required", Toast.LENGTH_LONG).show();
        }
    }

    private void createAccount(String email, String password){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){   // Sign in Successful
                        // Add user to database
                        mdatabase.writeNewUser(Objects.requireNonNull(auth.getCurrentUser()).getUid(),
                                Objects.requireNonNull(auth.getCurrentUser().getEmail()));
                        // Log
                        Log.d("createAccount", "createUserWithEmail:success");
                        // Swap activities
                        startActivity(new Intent(root.getContext(), DashboardActivity.class));
                        finish();
                    } else {
                        // Sign in Failed
                        Log.w("createAccount", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication Failed.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}