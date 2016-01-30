package com.example.android.allocate.alarms;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.android.allocate.R;
import com.example.android.allocate.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Jung on 2016-01-25.
 *
 * Once the alarm is finished recieves the broadcasted intent and handles it
 */
public class TimerDoneReceiver extends WakefulBroadcastReceiver {
    AlarmManager alarmManager;
    List<PendingIntent> pendingIntentsList = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification noti = new Notification.Builder(context)
                .setContentTitle("Task Done")
                .setContentText("Task Done")
                .setSmallIcon(R.drawable.ic_refresh_36pt)
                .build();
        int mNotificationId = 001;
// Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
// Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, noti);
        Log.i("TASK DONE", "TRIGGERED");
    }

    public void setAlarms(Context context, long timeLeft, long id) {
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimerDoneReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,(int) id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeLeft, pendingIntent);
        pendingIntentsList.add(pendingIntent);
    }

    public void cancelAlarms() {
        if(alarmManager != null){
            for(int i = 0; i < pendingIntentsList.size(); i++){
                alarmManager.cancel(pendingIntentsList.get(i));
            }
        }
    }
}
