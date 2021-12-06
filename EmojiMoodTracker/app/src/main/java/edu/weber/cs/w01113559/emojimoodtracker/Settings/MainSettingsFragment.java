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

        // Manage Notification Dependant Settings
        SwitchPreferenceCompat notificationsSwitch = findPreference("notificationSwitch");
        if (notificationsSwitch != null) {
            // Set Dependency Visibility
            updateNotificationDependentPreferences(notificationsSwitch.isChecked());
            // Create click listener
            notificationsSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                Boolean notificationValue = (Boolean) newValue;
                updateNotificationDependentPreferences(notificationValue);
                if (!notificationValue) {
                    // Notifications
                    // ToDo: Remove any existing notifications
                }
                return true;
            });
        }
    }

    /**
     * Updates the notification modification preferences based on whether it's parent is
     * enabled or not
     * @param notificationValue {@link Boolean} The state of the {@link SwitchPreferenceCompat} notificationSwitch.
     */
    private void updateNotificationDependentPreferences(Boolean notificationValue) {
        Preference scheduleReminder = findPreference("scheduleNotification");
        Preference removeReminder = findPreference("removeNotification");
        if (notificationValue) {
            // Notifications Enabled
            if (scheduleReminder != null) scheduleReminder.setVisible(true);
            if (removeReminder != null) removeReminder.setVisible(true);
        } else {
            // Notifications disabled
            if (scheduleReminder != null) scheduleReminder.setVisible(false);
            if (removeReminder != null) removeReminder.setVisible(false);
        }
    }

}