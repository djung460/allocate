package com.example.android.allocate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.android.allocate.db.TaskHandler;
import com.example.android.allocate.task.Task;

/**
 * Created by David Jung on 2016-01-25.
 */
public class TickReciever extends BroadcastReceiver {
    private TaskHandler mTaskHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        mTaskHandler.tick();
    }

    public void initialize(TaskHandler taskHandler){
        mTaskHandler = taskHandler;
    }
}
