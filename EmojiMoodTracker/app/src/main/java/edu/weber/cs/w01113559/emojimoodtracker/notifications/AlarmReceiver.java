package edu.weber.cs.w01113559.emojimoodtracker.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import edu.weber.cs.w01113559.emojimoodtracker.R;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.AppDatabase;
import edu.weber.cs.w01113559.emojimoodtracker.data.model.GlobalAppDatabase;

public class AlarmReceiver extends BroadcastReceiver {

    private String TAG = AlarmReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(context != null && intent != null && intent.getAction() != null) {
            if (intent.getAction()
                    .equalsIgnoreCase(context.getString(R.string.action_notify_update_mood))) {
                NotificationUtil.createNotification(
                        context,
                        NotificationDatabase.getDontForgetToRecordReminderData(context));
            }
        }
    }
}
