package com.example.android.allocate;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.allocate.db.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dooj on 2016-01-01.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.TaskHolder> {
    private List<Task> mDataSet;
    private Activity context;

    public MyAdapter(Activity context) {
        mDataSet = new ArrayList<>();
        this.context = context;
    }
    public void thisFuckingmethod(){

    }

    public void setTasks(List<Task> taskList) {
        mDataSet.clear();
        mDataSet.addAll(taskList);
        this.notifyItemRangeInserted(0,mDataSet.size() - 1);
    }

    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view, parent, false);
        TaskHolder vh = new TaskHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        holder.mTextView.setText(mDataSet.get(position).getTaskName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class TaskHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView mTextView;
        private Task mTask;

        public TaskHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.text_view_heading);
        }

        public void bindTask(Task task) {
            mTask = task;
            mTextView.setText(task.getTaskName());
        }
    }
}