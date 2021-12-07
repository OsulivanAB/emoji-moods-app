package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private View root;
    private static final String TAG = "emoji-mood-app";
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        setContentView(root);
        initData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed. " + e.getMessage(), e);
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * Initializes all variables
     */
    private void initData(){

        Context context = getApplicationContext();
        auth = FirebaseAuth.getInstance();
        setupClickListeners();
    }

    /**
     * Sets up all click listeners for the Activity
     */
    private void setupClickListeners(){
        // New User Button
        binding.llNewUser.setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), RegisterActivity.class));
            finish();
        });

        // Login Button
        binding.btnLogin.setOnClickListener(v -> getUserDate());

        // Google Sign in Button
        binding.googleButton.setOnClickListener(v -> {
            mGoogleSignInClient = createGoogleSignInRequest();
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);   // Will trigger onActivityResult()
        });
    }

    private void getUserDate() {
        String email = Objects.requireNonNull(binding.etEmail.getText()).toString();
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString();

        if (!email.isEmpty() && !password.isEmpty()) {
            signIn(email, password);
        } else {
            Snackbar.make(root, "All inputs required.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private void signIn(String email, String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        // Sign in success
                        Log.d(TAG, "signInWithEmail:success");
                        startActivity(new Intent(root.getContext(), DashboardActivity.class));
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Snackbar.make(root, "Authentication Failed.", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    /**
     * Create Google Sign in Request
     * @return {@link GoogleSignInClient}
     */
    private GoogleSignInClient createGoogleSignInRequest() {
        // Configure Google Sign In Options
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        return GoogleSignIn.getClient(this, gso);
    }

    /**
     * Signs in a user using Google.
     * @param idToken {@link String} Token to be used to sign in with google.
     */
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        AppDatabase.writeNewUser(Objects.requireNonNull(auth.getCurrentUser()).getUid(),
                                Objects.requireNonNull(auth.getCurrentUser().getEmail()));
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        startActivity(new Intent(root.getContext(), DashboardActivity.class));
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Snackbar.make(root, "Authentication Failed.", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                });
    }
}