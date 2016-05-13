package com.example.android.allocate.db;

import android.content.Context;

import com.example.android.allocate.adapter.TaskAdapter;
import com.example.android.allocate.task.Task;

import java.util.List;

/**
 * Created by David Jung on 2016-01-13.
 *
 * Handles the task and acts as an interface between the UI and Database
 */

public class TaskHandler {
    private TaskDatabaseHelper mTaskDatabaseHelper;
    private TaskAdapter mTaskAdapter;
    private List<Task> mDataset;
    private static long mTimePaused;
    private Context mContext;

    public TaskHandler(Context context) {
        mTaskDatabaseHelper = new TaskDatabaseHelper(context);
        mDataset = mTaskDatabaseHelper.getAllTasks();
        mTaskAdapter = new TaskAdapter(mDataset, this,context);
        mContext = context;
    }

    public void refresh() {
        mDataset.clear();
        mDataset.addAll(mTaskDatabaseHelper.getAllTasks());
        mTaskAdapter.notifyDataSetChanged();
    }

    /**
     * Counts down the task
     */
    public void tick() {
        for(int i = 0; i < mDataset.size();i++) {
            Task t = mDataset.get(i);
            if(t.isRunning()) {
                t.setNumTicks(t.numTicks() + 1);
                t.setTimeLeft(t.getTimeLeft() - 100);
                if(t.numTicks() >= 10) {
                    t.setNumTicks(0);
                    mTaskAdapter.notifyItemChanged(i);
                    if (t.getTimeLeft() <= 0) {
                        t.setTimeLeft(0);
                        t.pause();
                    }
                }
            }
        }
    }

    public void pause() {
        mTimePaused = System.currentTimeMillis();
        updateDatabase();
        refresh();
    }

    public void resume() {
        long timeElapsed = System.currentTimeMillis() - mTimePaused;

        for(Task t : mDataset){
            if(t.isRunning()){
                t.setTimeLeft(t.getTimeLeft() - timeElapsed);
            }
            if(t.getTimeLeft() <= 0){
                t.setTimeLeft(0);
                t.pause();
            }
            mTaskAdapter.notifyDataSetChanged();
        }

        updateDatabase();
        refresh();
    }

    public void updateDatabase() {
        mTaskDatabaseHelper.updateTable(mDataset);
    }

    public TaskDatabaseHelper getTaskDatabaseHelper() {
        return mTaskDatabaseHelper;
    }

    public TaskAdapter getTaskAdapter() {
        return mTaskAdapter;
    }

    public List<Task> getDataset() {
        return mDataset;
    }

    public void clearTask() {
        mTaskDatabaseHelper.clearTasks();
        refresh();
    }

    public void addTask(Task task) {
        mTaskDatabaseHelper.insertTask(task);
        refresh();
    }

    public void deleteTask(Task task) {
        mTaskDatabaseHelper.deleteTask(task.getId());
    }

    public void updateTask(Task task) {
        mTaskDatabaseHelper.updateTask(task);
    }
}

