package com.example.android.allocate.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.example.android.allocate.R;

/**
 * Created by Dooj on 2016-01-05.
 */
public class TaskChildViewHolder extends ChildViewHolder {
    public TextView mTaskDescription;
    public ImageButton mDeleteTask;
    public ImageButton mRestartTask;

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public TaskChildViewHolder(View itemView) {
        super(itemView);

        mTaskDescription = (TextView) itemView.findViewById(R.id.child_list_item_task_description);
        mDeleteTask = (ImageButton) itemView.findViewById(R.id.child_list_item_delete);
        mRestartTask = (ImageButton) itemView.findViewById(R.id.child_list_item_reset);
    }


}
