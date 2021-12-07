package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

import edu.weber.cs.w01113559.emojimoodtracker.notifications.NotificationUtil;

/**
 * Holds user settings for the emojiMoodTracker App.
 */
@IgnoreExtraProperties
public class Settings {

    //region Variables
    /**
     * {@link List} of {@link String} emoji codes.
     * You can encode and decode emoji's using {@link emojiEncoding}.
     */
    private List<String> emojiList;
    /**
     * {@link List} of {@link ReminderData} objects that represent the users reminders/notifications.
     */
    private List<ReminderData> reminders;
    //endregion

    //region Getters / Setters

    /**
     * Gets teh list of reminders
     * @return {@link List} list of {@link ReminderData}
     */
    public List<ReminderData> getReminders() {
        return reminders;
    }

    /**
     * Sets List of reminders.
     * ***DOES NOT UPDATE FIREBASE***
     * @param reminders {@link List} list of {@link ReminderData}
     */
    public void setReminders(List<ReminderData> reminders) {
        this.reminders = reminders;
    }

    /**
     * Sets the reminder list and updates the database.
     * @param context {@link Context} context for current application.
     * @param reminders {@link List<ReminderData>} list of {@link ReminderData}.
     */
    @Exclude
    public void setReminders(Context context, List<ReminderData> reminders) {
        deleteAlarmsForReminders(context);
        this.reminders = reminders;
        scheduleAlarmsForReminders(context);
    }

    /**
     * Add a {@link ReminderData} to the reminder list and updates the database
     * @param context {@link Context} context for current application.
     * @param reminder {@link ReminderData} reminder to be added.
     */
    @Exclude
    public void addReminder(Context context, ReminderData reminder) {
        this.reminders.add(reminder);
        updateDatabase(context);
    }

    /**
     * Removes a {@link ReminderData} from the reminder list and updates the database.
     * @param context {@link Context} context for current application.
     * @param id {@link String} ID for the {@link ReminderData} to be removed.
     * @return {@link Boolean} true- found and removed, false- reminder not found.
     */
    @Exclude
    public boolean removeReminder(Context context, String id) {
        if (this.reminders != null) {
            for (ReminderData reminder : reminders) {
                if (reminder.getID().equals(id)) {
                    this.reminders.remove(reminder);
                    updateDatabase(context);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a list of emoji codes.
     * @return {@link List} list of {@link String} emoji codes.
     */
    public List<String> getEmojiList() {
        return emojiList;
    }

    /**
     * Sets List of emoji codes.
     * ***DOES NOT UPDATE FIREBASE***
     * @param emojiList {@link List} list of {@link String} emoji codes.
     */
    public void setEmojiList(List<String> emojiList) {
        this.emojiList = emojiList;
    }

    /**
     * Add an emoji code to the emoji list and updates the database
     * @param context {@link Context} context for current application.
     * @param emoji {@link String} code for emoji.
     */
    @Exclude
    public void addEmoji(Context context, @NonNull String emoji) {
        this.emojiList.add(emoji);
        updateDatabase(context);
    }

    /**
     * Removes an emoji from the emoji list and updates the database.
     * @param context {@link Context} context for current application.
     * @param emoji {@link String} code for emoji to remove
     */
    @Exclude
    public boolean removeEmoji(Context context, @NonNull String emoji) {
        if (this.emojiList != null) {
            for (String _emoji : this.emojiList) {
                if (_emoji.equalsIgnoreCase(emoji)) {
                    this.emojiList.remove(_emoji);
                    updateDatabase(context);
                    return true;
                }
            }
        }
        return false;
    }
    //endregion

    //region Constructors
    public Settings() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Settings(List<String> emojiList, List<ReminderData> reminders) {
        this.emojiList = emojiList;
        this.reminders = reminders;
    }
    //endregion

    //region Alarm Managers
    public final void scheduleAlarmsForReminders(@NonNull Context context) {
        // Delete existing alarms first, just in case
        deleteAlarmsForReminders(context);
        if (reminders != null) {
            for (ReminderData reminderData : reminders) {
                NotificationUtil.scheduleAlarmsForReminder(context, reminderData);
            }
        }
    }

    public final void deleteAlarmsForReminders(@NonNull Context context) {
        if (reminders != null) {
            for (ReminderData reminderData : reminders) {
                NotificationUtil.removeAlarmsForReminder(context, reminderData);
            }
        }
    }
    //endregion

    /**
     * Updates the firebase database using {@link AppDatabase}.
     * Uses the current saved data.
     * @param context {@link Context} context for current application.
     */
    private void updateDatabase(Context context) {
        AppDatabase.writeUserSettings(context, this.emojiList, this.reminders);
    }
}
