package edu.weber.cs.w01113559.emojimoodtracker.notifications;

import android.app.NotificationManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public final class NotificationDatabase {

    public static DontForgetToRecordReminderData getDontForgetToRecordReminderData() {
        return DontForgetToRecordReminderData.getInstance();
    }

    /**
     * Data needed for just a basic reminder to update mood notification
     */
    public static class DontForgetToRecordReminderData extends NotificationData {

        private static DontForgetToRecordReminderData sInstance = null;

        public static DontForgetToRecordReminderData getInstance() {
            if (sInstance == null) {
                sInstance = getSync();
            }
            return sInstance;
        }

        private static synchronized DontForgetToRecordReminderData getSync() {
            if (sInstance == null) {
                sInstance = new DontForgetToRecordReminderData();
            }

            return sInstance;
        }

        private DontForgetToRecordReminderData() {

            // Standard Notification values:
            // Title for API <16 (4.0 and below) devices.
            mContentTitle = "Don't forget to...";
            // Content for API <24 (4.0 and below) devices.
            mContentText = "Update your mood!";
            mPriority = NotificationCompat.PRIORITY_DEFAULT;

            // Notification channel values (for devices targeting 26 and above)
            mChannelId = "channel_reminder_1";
            // User-visible name of the channel.
            mChannelName = "Update Mood Reminder";
            // User-visible description of channel.
            mChannelDescription = "Notifications to remind you to update your mood";
            mChannelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            mChannelEnableVibrate = true;
            mChannelLockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;
        }

        @NonNull
        @Override
        public String toString() {
            return getContentTitle() + getContentText();
        }
    }

    public abstract static class NotificationData {

        // Standard Notification Components
        protected String mContentTitle;
        protected String mContentText;
        protected int mPriority;

        // Notification channel values
        protected String mChannelId;                    // Not User Visible
        protected CharSequence mChannelName;            // User Visible
        protected String mChannelDescription;           // User Visible
        protected int mChannelImportance;               // Subject to change by user
        protected boolean mChannelEnableVibrate;        // Subject to change by user
        protected int mChannelLockscreenVisibility;     // Subject to change by user

        public String getContentTitle() {
            return mContentTitle;
        }

        public String getContentText() {
            return mContentText;
        }

        public int getPriority() {
            return mPriority;
        }

        public String getChannelId() {
            return mChannelId;
        }

        public CharSequence getChannelName() {
            return mChannelName;
        }

        public String getChannelDescription() {
            return mChannelDescription;
        }

        public int getChannelImportance() {
            return mChannelImportance;
        }

        public boolean isChannelEnableVibrate() {
            return mChannelEnableVibrate;
        }

        public int getChannelLockscreenVisibility() {
            return mChannelLockscreenVisibility;
        }
    }
}
