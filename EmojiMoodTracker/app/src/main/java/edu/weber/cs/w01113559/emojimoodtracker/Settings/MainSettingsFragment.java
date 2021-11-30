package edu.weber.cs.w01113559.emojimoodtracker.Settings;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.weber.cs.w01113559.emojimoodtracker.R;

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
    }
}