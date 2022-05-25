package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import edu.weber.cs.w01113559.emojimoodtracker.Settings.DeleteDataDialog;
import edu.weber.cs.w01113559.emojimoodtracker.Settings.MainSettingsFragment;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.databinding.ActivityDashboardBinding;
import edu.weber.cs.w01113559.emojimoodtracker.notifications.NotificationDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.notifications.NotificationUtil;

public class DashboardActivity extends AppCompatActivity implements
        PreferenceFragmentCompat.OnPreferenceStartFragmentCallback,
        LogoutDialog.signOutInterface {

    @SuppressWarnings("unused")
    private FirebaseAuth auth;

    @SuppressWarnings("unused")
    private View root;
    @SuppressWarnings("unused")
    private ActivityDashboardBinding binding;
    @SuppressWarnings("unused")
    private Toolbar toolbar;
    @SuppressWarnings("unused")
    private AppBarConfiguration appBarConfiguration;

    @SuppressWarnings("unused")
    private NavHostFragment navHostFragment;
    @SuppressWarnings("unused")
    private NavController navController;
    @SuppressWarnings("unused")
    private NavOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(root);
        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        // Create Notification channel if it doesn't already exist
        NotificationUtil.createNotificationChannel(
                getApplicationContext(),
                NotificationDatabase.getDontForgetToRecordReminderData(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.action_settings) {
            // Remove Graph Button
            setGraphFabVisibility(false);

            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.nav_host_fragment_content_main, new MainSettingsFragment())
                    .commit();
            return true;
        } else if (itemID == R.id.action_sign_out) {
            DialogFragment logoutDialog = new LogoutDialog(this);
            logoutDialog.show(getSupportFragmentManager(), "logoutDialog");
            return true;
        } else if (itemID == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Handles when the user clicks a {@link Preference}.
     * @param caller {@link PreferenceFragmentCompat} the fragment that called this.
     * @param pref {@link Preference} the preference that was clicked to trigger this.
     * @return {@link Boolean} true: handled. false: unable to handle.
     */
    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        switch (pref.getKey()) {
            case "scheduleNotification":
                //ToDo: Handle Schedule Notification Click
                return true;
            case "removeNotification":
                // ToDo: handle Remove Notification Click
                return true;
            case "clearData":
                DialogFragment clearDataDialog = new DeleteDataDialog();
                clearDataDialog.show(getSupportFragmentManager(), "clearDataDialog");
                return true;
            case "sendFeedback":

                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.feedback_url)));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    Log.d(MyApplication.TAG, e.getMessage());
                }
                return true;
        }
        return false;
    }

    /**
     * Initialize Activity Data
     */
    private void initData(){

        // Firebase
        auth = FirebaseAuth.getInstance();
        if (GlobalAppDatabase.getAppDatabaseInstance() == null) {
            GlobalAppDatabase.initializeAppDatabaseInstance();
        }

        // View Elements
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        toolbar = binding.myToolbar;
        toolbar.setTitle("Record Mood");

        // Navigation
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavOptions.Builder optionsBuilder = new NavOptions.Builder();
        options = optionsBuilder
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();
        binding.graphFAB.setOnClickListener(view -> {
            setGraphFabVisibility(false);
            navController.navigate(R.id.graphFragment, null, options);  // Navigate to Graph Page
            toolbar.setTitle("Mood Summary Graph");


        });
    }

    /**
     * Sets the {@link com.google.android.material.floatingactionbutton.FloatingActionButton} for the graph page as visible or not.
     *
     * @param visibility {@link Boolean} true- visible, false- hidden.
     */
    public void setGraphFabVisibility(@NonNull Boolean visibility) {
        if (visibility) binding.graphFAB.show();
        else binding.graphFAB.hide();
    }

    @Override
    public void signout() {
        if (auth != null && auth.getCurrentUser() != null) {
            auth = FirebaseAuth.getInstance();
            auth.signOut();
            Snackbar.make(root, "Successfully Logged Out.", Snackbar.LENGTH_SHORT).show();
            startActivity(new Intent(root.getContext(), LoginActivity.class));
            finish();
        } else {
            Snackbar.make(root, "There was an error signing out.", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets the status of the Home/Back Button in the {@link ActionBar}.
     *
     * @param status {@link Boolean} true: show back, arrow false: hide back arrow
     */
    public void setHomeButton(Boolean status) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(status);
            actionBar.setDisplayHomeAsUpEnabled(status);
            actionBar.setDisplayShowHomeEnabled(status);
        }
    }
}