package com.example.android.allocate.task;

import android.os.CountDownTimer;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.example.android.allocate.Day;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Dooj on 2015-12-25.
 */
public class Task {
    private boolean mRunning;
    private String mTitle;
    private int mId;
    private long mInitialTime;
    private long mTimeLeft;
    private ExpandedTask mExpandedTask;
    //TODO IMPLEMENT A TIMER

    public Task(int id, String title, String description, boolean status, long timeLeft, long initialTime ) {
        mTitle = title;
        mId = id;
        mInitialTime = initialTime;
        mTimeLeft = timeLeft;
        mExpandedTask = new ExpandedTask(description);
        mRunning = status;
    }

    public ExpandedTask getExpandedTask() {
        return mExpandedTask;
    }

    public boolean isRunning() {
        return mRunning;
    }

    public long getInitialTime() {
        return mInitialTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public long getTimeLeft() {
        return mTimeLeft;
    }

    public int getId() {
        return mId;
    }

    public void resetTimeLeft() {
        mTimeLeft = mInitialTime;
    }

    public void setTimeLeft(long time) {
        mTimeLeft = time;
    }

    public void pause(){
        mRunning = false;
    }

    public void start() {
        mRunning = true;
    }

}
