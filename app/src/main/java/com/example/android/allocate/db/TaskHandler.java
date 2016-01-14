package com.example.android.allocate.db;

import android.content.Context;
import android.text.method.MultiTapKeyListener;

import com.example.android.allocate.TaskAdapter;
import com.example.android.allocate.task.Task;

import java.util.List;

/**
 * Created by Dooj on 2016-01-13.
 */
public class TaskHandler {
    private TaskDatabaseHelper mTaskDatabaseHelper;
    private TaskAdapter mTaskAdapter;
    private List<Task> mDataset;

    public TaskHandler(Context context) {
        mTaskDatabaseHelper = new TaskDatabaseHelper(context);
        mDataset = mTaskDatabaseHelper.getAllTasks();
        mTaskAdapter = new TaskAdapter(mDataset, this);
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
        refresh();
    }

    public void updateTask(Task task) {
        mTaskDatabaseHelper.updateTask(task);
        refresh();
    }

    public void refresh() {
        mDataset.clear();
        mDataset.addAll(mTaskDatabaseHelper.getAllTasks());
        mTaskAdapter.notifyDataSetChanged();
    }

    //TODO FIX THIS METHOD CAN'T RUN TWO TIMERS AT ONCE UPDATE DATABASE ONLY ON PAUSE
    public void tick() {
        for(Task t : mDataset){
            if(t.isRunning()){
                t.setTimeLeft(t.getTimeLeft() - 1000);
                mTaskAdapter.notifyDataSetChanged();
            }
        }

    }
}
