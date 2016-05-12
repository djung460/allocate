package com.example.android.allocate;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by David Jung on 2016-01-13
 *
 * Temporary service that runs in the background when activity is on screen to update the
 * UI during ticking
 */
public class TimerBroadcastService extends Service {

    private final static String SERVICE_NAME = "BROADCAST_SERVICE";

    public static final String COUNTDOWN_BROADCAST = "COUNTDOWN_BROADCAST";
    Intent broadcastIntent = new Intent(COUNTDOWN_BROADCAST);

    CountDownTimer updatePeriod;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(SERVICE_NAME, "Timer Started");

        updatePeriod = new CountDownTimer(Long.MAX_VALUE,100) {
            @Override
            public void onTick(long millisUntilFinished) {
                //Log.i(SERVICE_NAME, "Time Remaining " + millisUntilFinished);
                broadcastIntent.putExtra("0.2 Second Passed",true);
                sendBroadcast(broadcastIntent);
            }

            @Override
            public void onFinish() {
                Log.i(SERVICE_NAME, "Second Elapsed");
            }
        };

        updatePeriod.start();
    }

    @Override
    public void onDestroy() {
        updatePeriod.cancel();
        Log.i(SERVICE_NAME,"Timer Cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
