package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.Settings;
import edu.weber.cs.w01113559.emojimoodtracker.databinding.FragmentEmojiBinding;

public class EmojiFragment extends Fragment {

    private final String TAG = "emoji-mood-tracker";
    private FragmentEmojiBinding binding;
    private AppDatabase mDatabase;

    public EmojiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmojiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setHasOptionsMenu(true);
        initData();

        // Setup Action Bar
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(false);

        }

        return root;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // Remove Settings Button
        MenuItem settingsButton = menu.findItem(R.id.action_settings);
        settingsButton.setVisible(true);
    }

    private void initData() {

        // Set Toolbar
        Toolbar toolbar = requireActivity().findViewById(R.id.my_toolbar);
        toolbar.setTitle("Record Mood");

        // Show FAB
        FloatingActionButton graphFab = requireActivity().findViewById(R.id.graphFAB);
        graphFab.show();

        // Get Database
        mDatabase = GlobalAppDatabase.getAppDatabaseInstance();
    }

    @Override
    public void onResume() {
        super.onResume();

        generateEmojiButtons();

//
//        // ToDo: This should be in a "Populate page" function
//        // Loop through Rows in the Table
//        for (int i = 0; i < binding.emojiTable.getChildCount(); i++) {
//            if (binding.emojiTable.getChildAt(i) instanceof TableRow) {
//                TableRow row = (TableRow) binding.emojiTable.getChildAt(i);
//                // Loop Through Items in the row
//                for (int j = 0; j < row.getChildCount(); j++) {
//                    if (row.getChildAt(j) instanceof AppCompatImageButton) {
//                        // Analyze the image
//                        AppCompatImageButton image = (AppCompatImageButton) row.getChildAt(j);
//                        image.setOnClickListener(emojiButtonListener);
//                    }
//                }
//            }
//        }
//        // ToDo: End ToDo
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Listener for almost all buttons
     */
    private View.OnClickListener emojiButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(binding.getRoot(), "You chose: " + v.getTag().toString() + ".", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null)
                    .show();
        }
    };

    /**
     * Creates the table emoji buttons based on the shared preferences.
     * ToDo: Make this section Work
     */
    private void generateEmojiButtons() {
        Context context = getContext();
        if (context != null) {
            List<String> emojiList = null;

            // Get user Preferences
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            Set<String> emojis = sharedPreferences.getStringSet("Emojis", null);

            // If it was not in preferences
            if (emojis == null) {   // Check Firebase

                AppDatabase mDatabase = GlobalAppDatabase.getAppDatabaseInstance();
                if (mDatabase == null) mDatabase = GlobalAppDatabase.initializeAppDatabaseInstance(context);

                // Get List saved in database
                Settings userSettings = AppDatabase.userSettings;
                if (userSettings != null) {
                    emojiList = userSettings.getEmojiList();
                }

                // If successful then convert to Has set
                if (emojiList != null && emojiList.size() != 0) {
                    emojis = new HashSet<>(emojiList);
                }

                // If unsuccessful get the dfaults
                else {
                    // Get Defaults
                    String[] emojisArray = context.getResources().getStringArray(R.array.default_emojis);
                    // Convert to Set<String>
                    emojis = new HashSet<>(Arrays.asList(emojisArray));
                }

                // Update User Preferences
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                prefsEditor.putStringSet("Emojis", emojis).commit();
            }

            if (emojiList == null) {
                emojiList = new ArrayList<>();
                emojiList.addAll(emojis);
            }

            RecyclerView recyclerView = binding.emojiRV;
            emojiGridAdapter adapter = new emojiGridAdapter(context, emojiList);

            recyclerView.setLayoutManager( new GridLayoutManager(context, 3));
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(false);
        }
    }
}