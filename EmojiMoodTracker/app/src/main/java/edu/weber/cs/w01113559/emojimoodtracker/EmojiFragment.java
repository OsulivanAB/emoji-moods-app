package edu.weber.cs.w01113559.emojimoodtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @SuppressWarnings("unused")
    private FragmentEmojiBinding binding;

    public EmojiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmojiBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        ((DashboardActivity) requireActivity()).setHomeButton(false);
        return binding.getRoot();
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
        AppDatabase mDatabase = GlobalAppDatabase.getAppDatabaseInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        generateEmojiButtons();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }


    /**
     * Populates Emoji Button Array.
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
                if (mDatabase == null) mDatabase = GlobalAppDatabase.initializeAppDatabaseInstance();

                // Get List saved in database
                Settings userSettings = AppDatabase.userSettings;
                if (userSettings != null) {
                    emojiList = userSettings.getEmojiList();
                }

                // If successful then convert to Has set
                if (emojiList != null && emojiList.size() != 0) {
                    emojis = new HashSet<>(emojiList);
                }

                // If unsuccessful get the defaults
                else {
                    // Get Defaults
                    String[] emojisArray = context.getResources().getStringArray(R.array.default_emojis);
                    // Convert to Set<String>
                    emojis = new HashSet<>(Arrays.asList(emojisArray));
                }

                // Update User Preferences
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                prefsEditor.putStringSet("Emojis", emojis).apply();
            }

            if (emojiList == null) {
                emojiList = new ArrayList<>(emojis);
            }

            RecyclerView recyclerView = binding.emojiRV;
            emojiGridAdapter adapter = new emojiGridAdapter(context, emojiList);

            recyclerView.setLayoutManager( new GridLayoutManager(context, 3));
            recyclerView.setAdapter(adapter);
            recyclerView.setHasFixedSize(false);
        }
    }
}