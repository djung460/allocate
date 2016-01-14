package com.example.android.allocate;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.allocate.ViewHolders.TaskViewHolder;
import com.example.android.allocate.db.TaskDatabaseHelper;
import com.example.android.allocate.db.TaskHandler;
import com.example.android.allocate.task.Task;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<Task> mDataset;
    private TaskHandler mTaskHandler;

    public TaskAdapter(List<Task> dataset, TaskHandler taskHandler){
        mDataset = dataset;
        mTaskHandler = taskHandler;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task_parent,parent,false);

        TaskViewHolder vh = new TaskViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        final int pos = position;
        final Task task = mDataset.get(position);
        long millis = task.getTimeLeft();

        holder.mTaskTitle.setText(task.getTitle());

        holder.mTaskTimeRemaining.setText(
                String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millis),
                        TimeUnit.MILLISECONDS.toMinutes(millis) -
                                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                        TimeUnit.MILLISECONDS.toSeconds(millis) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
                )
        );


        if(task.isRunning())
            holder.mStartStop.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_48dp);
        else
            holder.mStartStop.setBackgroundResource(R.drawable.ic_play_circle_outline_black_48dp);

        holder.mTaskDescription.setText(task.getExpandedTask().getDescription());

        holder.mDeleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.remove(pos);
                mTaskHandler.deleteTask(task);
                notifyDataSetChanged();
            }
        });

        holder.mResetTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataset.get(position).resetTimeLeft();
                mTaskHandler.updateTask(task);
                notifyDataSetChanged();

            }
        });

        holder.mStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDataset.get(position).isRunning()){
                    mDataset.get(position).pause();
                } else {
                    mDataset.get(position).start();
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}