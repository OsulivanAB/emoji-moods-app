package edu.weber.cs.w01113559.emojimoodtracker.Settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.weber.cs.w01113559.emojimoodtracker.R;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;

public class MainSettingsFragment extends PreferenceFragmentCompat {

    private SwitchPreferenceCompat notificationSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Hide Graph Button
        FloatingActionButton graphFab = requireActivity().findViewById(R.id.graphFAB);
        graphFab.hide();

        MultiSelectListPreference customizeEmojis = findPreference("Emojis");
        if (customizeEmojis != null) {
            customizeEmojis.setOnPreferenceChangeListener((preference, newValue) -> {

                List<String> emojiList = new ArrayList<>();
                emojiList.addAll( (HashSet<String>) newValue);
                AppDatabase mDatabase = GlobalAppDatabase.getAppDatabaseInstance();
                if (mDatabase == null) {
                    mDatabase = GlobalAppDatabase.initializeAppDatabaseInstance(getContext());
                }
                mDatabase.writeEmojiList(getContext(), emojiList);

                return true;
            });
        }
    }

}