package com.example.android.allocate;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.allocate.ViewHolders.TaskViewHolder;
import com.example.android.allocate.task.Task;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<Task> mDataset;

    public TaskAdapter(List<Task> dataset){
        mDataset = dataset;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task_parent,parent,false);

        TaskViewHolder vh = new TaskViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        Task task = mDataset.get(position);
        long millis = task.getTimeLeft();

        holder.mTaskTitle.setText(task.getTitle());

        holder.mTaskTimeRemaining.setText(
                String.format("%2d:%2d:%2d",
                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) -
                                TimeUnit.MINUTES.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                )
        );

        if(task.isRunning())
            holder.mStartStop.setBackgroundResource(R.drawable.ic_play_circle_outline_black_48dp);
        else
            holder.mStartStop.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_48dp);

        holder.mTaskDescription.setText(task.getExpandedTask().getDescription());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}