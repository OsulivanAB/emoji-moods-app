package edu.weber.cs.w01113559.emojimoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import edu.weber.cs.w01113559.emojimoodtracker.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ActivityRegisterBinding binding;
    private View root;

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
        clickListener();
    }

    private void clickListener(){
        binding.btnRegister.setOnClickListener(v -> validateUser());

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
                    if (task.isSuccessful()){
                        // Sign in Successful
                        Log.d("createAccount", "createUserWithEmail:success");
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