package edu.weber.cs.w01113559.emojimoodtracker.notifications;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.Locale;

import edu.weber.cs.w01113559.emojimoodtracker.MainActivity;
import edu.weber.cs.w01113559.emojimoodtracker.R;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.ReminderData;

public class NotificationUtil {

    /**
     * Creates a {@link NotificationChannel} if there isn't one already created on the device.
     *
     * @param context {@link Context} current application context
     * @param data    {@link NotificationDatabase.NotificationData} Reminder Data for this notification
     */
    public static void createNotificationChannel(Context context, NotificationDatabase.NotificationData data) {

        // Notification Channels are only necessary post Android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The ID of the channel.
            String channelID = data.getChannelId();

            // The user-visible name of the channel.
            CharSequence channelName = data.getChannelName();
            // The user-visible description of the channel
            String channelDescription = data.getChannelDescription();
            int channelImportance = data.getChannelImportance();
            boolean channelEnableVibrate = data.isChannelEnableVibrate();
            int channelLockscreenVisibility = data.getChannelLockscreenVisibility();

            // Initializes NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(channelDescription);
            notificationChannel.enableVibration(channelEnableVibrate);
            notificationChannel.setLockscreenVisibility(channelLockscreenVisibility);

            // Add NotificationChannel to system. Attempting to create an existing notification
            // channel with its original values performs no operation, so it's safe to perform the
            // below sequence
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }
    }

    /**
     * Creates a notification for {@link ReminderData} with a full notification tap and a single action.
     * @param context {@link Context} current application context
     * @param data {@link ReminderData}: Reminder Data for this notification
     */
    public static void createNotification(Context context, NotificationDatabase.DontForgetToRecordReminderData data) {
        // Create builder
        NotificationCompat.Builder builder = buildNotification(context, data);
        // call notify
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(198030, builder.build());
    }

    /**
     * Builds and returns the {@link NotificationCompat.Builder} for the notification
     * @param context {@link Context} current application context.
     * @param data {@link NotificationDatabase.DontForgetToRecordReminderData} data for this notification.
     * @return {@link NotificationCompat.Builder} Builder for the notification.
     */
    @NonNull
    private static NotificationCompat.Builder buildNotification(Context context, @NonNull NotificationDatabase.DontForgetToRecordReminderData data) {
        // Create an intent to handle when notification is clicked
        Intent notifyIntent = new Intent(context, MainActivity.class);
        // Create a TaskStack Builder which will inflate the backstack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Add new intent on top of the inflated back stack
        stackBuilder.addNextIntentWithParentStack(notifyIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent notifyPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, data.getChannelId())
                .setSmallIcon(R.drawable.emoji_u1f427)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.emoji_u1f427))
                .setContentTitle(data.getContentTitle())
                .setContentText(data.getContentText())
                .setColor(ContextCompat.getColor(context, R.color.teal_700))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(data.getPriority())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)
                .setContentIntent(notifyPendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setCategory(Notification.CATEGORY_REMINDER);
        }

        return builder;
    }

    /**
     * Creates a {@link PendingIntent} for the Alarm using the {@link ReminderData}
     *  @param context {@link Context} current application context
     * @param reminderData {@link ReminderData} ReminderData for the notification
     * @param day {@link String} String representation of the day
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    private static PendingIntent createPendingIntent(Context context, @NonNull ReminderData reminderData, String day) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(context.getString(R.string.action_notify_update_mood));
        intent.setType(day + reminderData.getID());
        intent.putExtra(MainActivity.KEY_ID, reminderData.getID());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Schedules all the alarms for {@link ReminderData}.
     * @param context {@link Context} current application context
     * @param reminderData {@link ReminderData} ReminderData to use for the alarm
     */
    public static void scheduleAlarmsForReminder(Context context, ReminderData reminderData) {

        // Get Alarm Manager reference
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Schedule the alarms based on the days
        String[] days = context.getResources().getStringArray(R.array.days);
        if (reminderData.getDays() != null) {
            for (String day : reminderData.getDays()) {
                if (day != null) {
                    // Create Pending intent
                    PendingIntent alarmIntent = createPendingIntent(context, reminderData, day);

                    // schedule the alarm
                    int dayOfWeek = getDayOfWeek(days, day);
                    scheduleAlarm(reminderData, dayOfWeek, alarmIntent, alarmManager);
                }
            }
        }
    }

    /**
     * Removes the notification if it was previously scheduled.
     *
     * @param context {@link Context} current application context
     * @param reminderData {@link ReminderData} ReminderData for the notification
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    public static void removeAlarmsForReminder(Context context, ReminderData reminderData) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setAction(String.valueOf(R.string.action_notify_update_mood));
        intent.putExtra(MainActivity.KEY_ID, reminderData.getID());

        // type must be unique so Intent.filterEquals passes the check to make distinct PendingIntents
        // Schedule the alarms based on the days
        if (reminderData.getDays() != null) {
            for (String day : reminderData.getDays()) {
                if (day != null) {
                    intent.setType(day + reminderData.getID());
                    PendingIntent alarmIntent = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                    else
                        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(alarmIntent);
                }
            }
        }
    }

    /**
     * Returns the int representation for the day of the week.
     *
     * @param days {@link String[]} array from resources
     * @param dayOfWeek {@link String} String representation of the day e.g "Sunday"
     * @return {@link Integer} - for given dayOfWeek
     */
    private static int getDayOfWeek(@NonNull String[] days, @NonNull String dayOfWeek) {
        if (dayOfWeek.equalsIgnoreCase(days[0])) return 1;
        if (dayOfWeek.equalsIgnoreCase(days[1])) return 2;
        if (dayOfWeek.equalsIgnoreCase(days[2])) return 3;
        if (dayOfWeek.equalsIgnoreCase(days[3])) return 4;
        if (dayOfWeek.equalsIgnoreCase(days[4])) return 5;
        if (dayOfWeek.equalsIgnoreCase(days[5])) return 6;
        return 7;
    }

    /**
     * Schedules a single alarm.
     * @param reminderData {@link ReminderData} Data for the alarm that we are making.
     * @param dayOfWeek {@link Integer} day of the week.
     * @param alarmIntent {@link PendingIntent} intent to be linked with alarm.
     * @param alarmManager {@link AlarmManager} alarm manager
     */
    private static void scheduleAlarm(@NonNull ReminderData reminderData, int dayOfWeek, PendingIntent alarmIntent, AlarmManager alarmManager) {

        // Set up time to schedule the alarm
        Calendar dateTimeToAlarm = Calendar.getInstance(Locale.getDefault());
        dateTimeToAlarm.setTimeInMillis(System.currentTimeMillis());
        dateTimeToAlarm.set(Calendar.HOUR_OF_DAY, reminderData.getHour());
        dateTimeToAlarm.set(Calendar.MINUTE, reminderData.getMinute());
        dateTimeToAlarm.set(Calendar.SECOND, 0);
        dateTimeToAlarm.set(Calendar.MILLISECOND, 0);
        dateTimeToAlarm.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        // Compare the dateTimeToAlarm to today to see if it needs to run today
        Calendar today = Calendar.getInstance(Locale.getDefault());
        if (shouldNotifyToday(dayOfWeek, today, dateTimeToAlarm)) {
            // Schedule for today
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    dateTimeToAlarm.getTimeInMillis(),
                    1000 * 60 * 60 * 24 * 7,
                    alarmIntent);
            return;
        }

        // Schedule 1 week out from the day
        dateTimeToAlarm.roll(Calendar.WEEK_OF_YEAR, 1);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                dateTimeToAlarm.getTimeInMillis(),
                1000 * 60 * 60 * 24 * 7,
                alarmIntent);
    }

    /**
     * Determines if the Alarm should be scheduled for today.
     * @param dayOfWeek Int: day of the week
     * @param today Calendar: today's datetime
     * @param dateTimeToAlarm Calendar: Alarm's datetime
     * @return boolean
     */
    private static boolean shouldNotifyToday(int dayOfWeek, @NonNull Calendar today, Calendar dateTimeToAlarm) {
        return dayOfWeek == today.get(Calendar.DAY_OF_WEEK) &&
                today.get(Calendar.HOUR_OF_DAY) <= dateTimeToAlarm.get(Calendar.HOUR_OF_DAY) &&
                today.get(Calendar.MINUTE) <= dateTimeToAlarm.get(Calendar.MINUTE);
    }
}
