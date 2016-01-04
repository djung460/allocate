package com.example.android.allocate.db;

import android.os.CountDownTimer;

/**
 * Created by Dooj on 2015-12-25.
 */
public class Task {
    private boolean isComplete;
    private String taskName;
    private String description;
    private int id;

    public Task() {
    }

    public Task(int id, String taskName, String description, boolean status) {
        this.taskName = taskName;
        this.id = id;
        this.description = description;
        isComplete = status;
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
