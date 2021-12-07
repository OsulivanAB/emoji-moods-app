package edu.weber.cs.w01113559.emojimoodtracker.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import edu.weber.cs.w01113559.emojimoodtracker.R;

public class AlarmReceiver extends BroadcastReceiver {

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
