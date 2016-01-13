package com.example.android.allocate.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.example.android.allocate.R;

/**
 * Created by Dooj on 2016-01-05.
 */
public class TaskViewHolder extends RecyclerView.ViewHolder {
    public TextView mTaskTitle;
    public TextView mTaskTimeRemaining;
    public ImageButton mStartStop;
    public ImageButton mParentDropDown;

    //Child views
    public TextView mTaskDescription;
    public ImageButton   mDeleteTaskButton;
    public ImageButton   mResetTaskButton;


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
        mParentDropDown = (ImageButton) itemView.findViewById(R.id.parent_list_item_expand_arrow);
        //Child views
        mTaskDescription = (TextView) itemView.findViewById(R.id.child_list_item_task_description);
        mDeleteTaskButton = (ImageButton) itemView.findViewById(R.id.child_list_item_delete);
        mResetTaskButton = (ImageButton) itemView.findViewById(R.id.child_list_item_reset);

    }
}