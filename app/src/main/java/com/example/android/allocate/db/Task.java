package com.example.android.allocate.db;

import android.os.CountDownTimer;

/**
 * Created by Dooj on 2015-12-25.
 */
public class Task {
    private boolean isComplete;
    private String taskName;
    private int id;

    public Task() {
    }

    public Task(int id, String taskName) {
        this.taskName = taskName;
        this.id = id;
        isComplete = false;

    }

    public boolean isComplete() {
        return isComplete;
    }

    public String getTaskName() {
        return taskName;
    }

    public int getId() {
        return id;
    }
}
