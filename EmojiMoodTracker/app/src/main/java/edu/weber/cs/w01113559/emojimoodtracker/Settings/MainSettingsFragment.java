package edu.weber.cs.w01113559.emojimoodtracker.Settings;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import edu.weber.cs.w01113559.emojimoodtracker.BuildConfig;
import edu.weber.cs.w01113559.emojimoodtracker.DashboardActivity;
import edu.weber.cs.w01113559.emojimoodtracker.R;

public class MainSettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        setHasOptionsMenu(true);
        ((DashboardActivity) requireActivity()).setHomeButton(true);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Remove Settings Button
        MenuItem settingsButton = menu.findItem(R.id.action_settings);
        settingsButton.setVisible(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupNotificationCategory();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((DashboardActivity) requireActivity()).setGraphFabVisibility(false);
        Preference version = findPreference("version");
        if (version != null) version.setSummary(BuildConfig.VERSION_NAME);
    }

    /**
     * Updates the notification modification preferences based on whether it's parent is
     * enabled or not
     *
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

    /**
     * Sets up functionality of the "Notifications" category.
     */
    private void setupNotificationCategory() {

        // Notification On/Off Switch
        SwitchPreferenceCompat notificationsSwitch = findPreference("notificationSwitch");
        if (notificationsSwitch != null) {
            // Set Dependency Visibility
            updateNotificationDependentPreferences(notificationsSwitch.isChecked());
            // Create click listener
            notificationsSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
                Boolean notificationSwitchState = (Boolean) newValue;
                // Set Visibility of Dependency Preferences
                updateNotificationDependentPreferences((Boolean) newValue);
                // Remove notifications if user is turning off (But not deleting them from shared preferences)
                if (notificationSwitchState) {
                    // Todo: Add all notifications already in the shared preferences
                } else {
                    // Todo: Loop through shared preferences de-activating (but not deleting) notifications.
                }
                return true;
            });
        }

        // Todo: Notification Settings

        // Todo: Schedule Notification
        //  - Update Shared Preferences with notification details
        //  - Add Notification to device

        // Todo: Remove Notification
        //  - Remove Notification from device
        //  - Update Shared Preferences to remove notification details
    }
}