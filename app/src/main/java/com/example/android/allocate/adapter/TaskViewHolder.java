package com.example.android.allocate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.android.allocate.R;

/**
 * Created by David Jung on 2016-01-05.
 *
 * View holder for each list item
 */
public class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView mTaskTitle;
    public TextView mTaskTimeRemaining;
    public ImageButton mStartStop;
//    public ImageButton   mResetTaskButton;


    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public TaskViewHolder(View itemView) {
        super(itemView);
        mTaskTitle = (TextView) itemView.findViewById(R.id.parent_list_item_task_title);
        mTaskTimeRemaining = (TextView) itemView.findViewById(R.id.parent_list_item_task_time);
        mStartStop = (ImageButton) itemView.findViewById(R.id.button_start_stop);
//        mResetTaskButton = (ImageButton) itemView.findViewById(R.id.child_list_item_reset);

    }
}
