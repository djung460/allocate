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
public class Task implements ParentListItem{
    private boolean mStatus;
    private boolean mRunning;
    private String mTitle;
    private int mId;
    private long mInitialTime;
    private long mTimeLeft;
    private ExpandedTask mExpandedTask;
    //TODO IMPLEMENT A TIMER

    private List<ExpandedTask> mExpandedTaskList;

    public Task(int id, String title, String description, boolean status, long time ) {
        mTitle = title;
        mId = id;
        mInitialTime = time;
        mTimeLeft = time;
        mExpandedTask = new ExpandedTask(description);
        mExpandedTaskList = new ArrayList<>();
        mExpandedTaskList.add(mExpandedTask);
        mStatus = status;
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

    public boolean getStatus() {
        return mStatus;
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


    public List<ExpandedTask> getExpandedTaskList() {
        return mExpandedTaskList;
    }

    public void setExpandedTaskList(List<ExpandedTask> list) {
        mExpandedTaskList = list;
    }

    @Override
    public List<ExpandedTask> getChildItemList() {
        return mExpandedTaskList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
