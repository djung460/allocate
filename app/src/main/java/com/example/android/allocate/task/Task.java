package com.example.android.allocate.task;

/**
 * Created by David Jung on 2015-12-25.
 *
 * Task object that users can assign the amount of time they wish to allocate.
 */
public class Task {
    private boolean mRunning;
    private String mTitle;
    private long mId;
    private long mInitialTime;
    private long mTimeLeft;
    private int numTicks;

    public Task(long id, String title, boolean status, long timeLeft, long initialTime ) {
        mTitle = title;
        mId = id;
        mInitialTime = initialTime;
        mTimeLeft = timeLeft;
        mRunning = status;
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

    public long getId() {
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

    public int numTicks() {
        return numTicks;
    }

    public void setNumTicks(int n) {
        numTicks = n;
    }
}
