package edu.weber.cs.w01113559.emojimoodtracker.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.util.DataUtils;

import edu.weber.cs.w01113559.emojimoodtracker.MainActivity;
import edu.weber.cs.w01113559.emojimoodtracker.R;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.NotificationDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.ReminderData;

public class AlarmReceiver extends BroadcastReceiver {

    private String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (GlobalAppDatabase.getAppDatabaseInstance() == null) {
            GlobalAppDatabase.setAppDatabaseInstance(new AppDatabase(context));
        }

        AppDatabase mDatabase = GlobalAppDatabase.getAppDatabaseInstance();

        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        if(context != null && intent != null && intent.getAction() != null) {
            if (intent.getAction()
                    .equalsIgnoreCase(String.valueOf(R.string.action_notify_update_mood))) {
                NotificationUtil.createNotification(
                        context,
                        NotificationDatabase.getDontForgetToRecordReminderData());
            }
        }
    }
}
