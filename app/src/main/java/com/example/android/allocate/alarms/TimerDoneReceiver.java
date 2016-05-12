package com.example.android.allocate.alarms;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
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

        Intent myIntent = new Intent(context, MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText("Task Done")
                .setSmallIcon(R.drawable.ic_hourglass_full_white_48pt_3x)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSound(alarmSound)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000})
                .setLights(Color.BLUE,3000,3000)
                .setContentIntent(pendingIntent)
                .build();

        int notificationId = (int) intent.getLongExtra("id",0);

        // Gets an instance of the NotificationManager service
        NotificationManager notifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Builds the notification and issues it.
        notifyMgr.notify(notificationId, notification);
        Log.i("TASK DONE", "TRIGGERED");
    }

    public void setAlarms(Context context, long timeLeft, long id, String title) {
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimerDoneReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("id",id);
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
