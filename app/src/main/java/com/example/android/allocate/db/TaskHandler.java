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
        mTaskAdapter = new TaskAdapter(mDataset);
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

    public void refresh() {
        mDataset.clear();
        mDataset.addAll(mTaskDatabaseHelper.getAllTasks());
        mTaskAdapter.notifyDataSetChanged();
    }
}
