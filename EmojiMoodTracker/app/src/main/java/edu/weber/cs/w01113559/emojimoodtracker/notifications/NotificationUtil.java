package edu.weber.cs.w01113559.emojimoodtracker.notifications;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import edu.weber.cs.w01113559.emojimoodtracker.MainActivity;
import edu.weber.cs.w01113559.emojimoodtracker.R;

public class NotificationUtil {

    public static final int NOTIFICATION_ID = 198030;

    private static String createNotificationChannel(Context context, NotificationDatabase.NotificationData notificationData){

        // Notification Channels are only necessary post Android O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // The ID of the channel.
            String channelID = notificationData.getChannelId();

            // The user-visible name of the channel.
            CharSequence channelName = notificationData.getChannelName();
            // The user-visible description of the channel
            String channelDescription = notificationData.getChannelDescription();
            int channelImportance = notificationData.getChannelImportance();
            boolean channelEnableVibrate = notificationData.isChannelEnableVibrate();
            int channelLockscreenVisibility = notificationData.getChannelLockscreenVisibility();

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

            return channelID;
        } else {
            // Returns null for pre-O (26) devices.
            return null;
        }
    }

    public static void createNotification(Context context) {

        // Get Data
        NotificationDatabase.DontForgetToRecordReminderData notificationData = NotificationDatabase.getDontForgetToRecordReminderData();
        // Create/Retrieve Notification Channel forO and beyond devices(26+)
        String notificationChannelID = createNotificationChannel(context, notificationData);
        // Create Builder if there isn't one yet
        assert notificationChannelID != null;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, notificationChannelID);
        GlobalNotificationBuilder.setNotificationCompatBuilderInstance(builder);
        // Create an intent to handle when notification is clicked
        Intent notifyIntent = new Intent(context, MainActivity.class);
        // Create a TaskStack Builder which will inflate the backstack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Add new intent on top of the inflated back stack
        stackBuilder.addNextIntentWithParentStack(notifyIntent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent notifyPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // Create Notification
        Notification notification = builder
                // Title for API <16 (4.0 and below) devices.
                .setContentTitle(notificationData.getContentTitle())
                // Content for API <24 (7.0 and below) devices.
                .setContentText(notificationData.getContentText())
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.emoji_u1f427))
                .setSmallIcon(R.drawable.emoji_u1f427)
                .setColor(ContextCompat.getColor(context, R.color.teal_700))
                .setContentIntent(notifyPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setCategory(Notification.CATEGORY_REMINDER)
                // Sets priority for 25 and below. For 26 and above, 'priority' is deprecated for
                // 'importance' which is set in the NotificationChannel. The integers representing
                // 'priority' are different from 'importance', so make sure you don't mix them.
                .setPriority(notificationData.getPriority())
                // Sets lock-screen visibility for 25 and below. For 26 and above, lock screen
                // visibility is set in the NotificationChannel.
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                // Adds additional actions specified above.
//                .addAction(snoozeAction)
//                .addAction(dismissAction)
                .build();

        NotificationManagerCompat mNotificationManagerCompat = NotificationManagerCompat.from(context);
        mNotificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }
}
