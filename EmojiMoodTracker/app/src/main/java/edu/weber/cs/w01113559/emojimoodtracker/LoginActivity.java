package edu.weber.cs.w01113559.emojimoodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import edu.weber.cs.w01113559.emojimoodtracker.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);
        initData();
    }

    private void initData(){
        auth = FirebaseAuth.getInstance();
        clickListener();
    }

    private void clickListener(){
        binding.llNewUser.setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), RegisterActivity.class));
            finish();
        });

        binding.btnLogin.setOnClickListener(v -> getUesrDate());
    }

    private void getUesrDate(){
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString();

        if (!email.isEmpty() && !password.isEmpty()){
            signIn(email, password);
        } else {
            Toast.makeText(this, "All inputs required.", Toast.LENGTH_LONG).show();
        }
    }

    private void signIn(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        // Sign in success
                        Log.d("signIn", "signInWithEmail:success");
                        startActivity(new Intent(root.getContext(), DashboardActivity.class));
                    } else {
                        Log.w("signIn", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}