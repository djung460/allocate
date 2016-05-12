package com.example.android.allocate.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.allocate.R;
import com.example.android.allocate.db.TaskHandler;
import com.example.android.allocate.task.Task;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<Task> mDataset;
    private TaskHandler mTaskHandler;
    private Context mContext;

    public TaskAdapter(List<Task> dataset, TaskHandler taskHandler, Context context) {
        mDataset = dataset;
        mTaskHandler = taskHandler;
        mContext = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task_parent, parent, false);

        TaskViewHolder vh = new TaskViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, final int position) {
        final int pos = position;
        final Task task = mDataset.get(position);
        long millis = task.getTimeLeft();

        holder.mTaskTitle.setText(task.getTitle());

        String time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );

        holder.mTaskTimeRemaining.setText(time);

        holder.mTaskTimeRemaining.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mDataset.get(position).resetTimeLeft();
                mDataset.get(position).pause();
                mTaskHandler.updateTask(task);
                notifyDataSetChanged();
                Toast.makeText(mContext,"Task Reset",Toast.LENGTH_SHORT);
                return true;
            }
        });


        if (task.isRunning()) {
            holder.mStartStop.setImageResource(R.drawable.ic_pause_white_48dp);
        }
        else {
            holder.mStartStop.setImageResource(R.drawable.ic_play_arrow_white_48dp);
        }


        holder.mStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mDataset.get(position).isRunning()) {
                    mDataset.get(position).pause();
                } else {
                    mDataset.get(position).start();
                }
                notifyItemChanged(position);
            }
        });

    }

    public void deleteItem(int pos) {
        mTaskHandler.deleteTask(mDataset.get(pos));
        mDataset.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}