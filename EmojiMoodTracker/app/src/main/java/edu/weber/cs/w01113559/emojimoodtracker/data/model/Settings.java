package edu.weber.cs.w01113559.emojimoodtracker.data.model;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

import edu.weber.cs.w01113559.emojimoodtracker.notifications.NotificationUtil;

@IgnoreExtraProperties
public class Settings {

    private List<String> emojiList;
    private List<ReminderData> reminders;

    public List<ReminderData> getReminders() {
        return reminders;
    }

    // ***** this version should only be used by Firebase *****
    public void setReminders(List<ReminderData> reminders) {
        this.reminders = reminders;
    }

    @Exclude
    public void setReminders(Context context, List<ReminderData> reminders) {
        deleteAlarmsForReminders(context);
        this.reminders = reminders;
        scheduleAlarmsForReminders(context);
    }

    public List<String> getEmojiList() {
        return emojiList;
    }

    public void setEmojiList(List<String> emojiList) {
        this.emojiList = emojiList;
    }

    @Exclude
    public void addEmoji(@NonNull String emoji) {
        this.emojiList.add(emoji);
    }

    @Exclude
    public void removeEmoji(@NonNull String emoji) {
        this.emojiList.remove(emoji);
    }

    public Settings() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Settings(List<String> emojiList, List<ReminderData> reminders) {
        this.emojiList = emojiList;
        this.reminders = reminders;
    }

    public final void scheduleAlarmsForReminders(@NonNull Context context) {
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
}
