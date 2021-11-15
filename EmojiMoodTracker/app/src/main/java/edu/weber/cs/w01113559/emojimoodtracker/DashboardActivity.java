package edu.weber.cs.w01113559.emojimoodtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.weber.cs.w01113559.emojimoodtracker.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private View root;
    private ActivityDashboardBinding binding;
    private Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;

    private NavHostFragment navHostFragment;
    private NavController navController;
    private NavOptions options;
    private NavOptions.Builder optionsBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        setContentView(root);
        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        binding.fab.setOnClickListener(view -> {
            navController.navigate(R.id.graphFragment, null, options);  // Navigate to Graph Page
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);  // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                navController.navigate(R.id.settingsFragment, null, options);   // Navigate to Settings Page
                return true;
            case R.id.action_sign_out:
                auth.signOut();
                startActivity(new Intent(root.getContext(), LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Initialize variables
     */
    private void initData(){
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        root = binding.getRoot();
        toolbar = binding.toolbar;
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        optionsBuilder = new NavOptions.Builder();
        options = optionsBuilder.setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build();
    }
}