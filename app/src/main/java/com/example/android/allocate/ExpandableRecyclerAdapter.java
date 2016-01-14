package com.example.android.allocate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.allocate.ViewHolders.TaskChildViewHolder;
import com.example.android.allocate.ViewHolders.TaskViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dooj on 2016-01-13.
 */
public abstract class ExpandableRecyclerAdapter<T extends TaskChildViewHolder> extends RecyclerView.Adapter<TaskViewHolder> {
    protected Context mContext;
    protected List<T> mDataset;
    protected List<T> mVisibleDataset;
    private List<Integer> mIndexList;
    private SparseIntArray mExpandMap;

    protected static final int TYPE_HEADER = 100;

    private static final int ARROW_ROTATION_DURATION = 150;

    public ExpandableRecyclerAdapter(Context context) {
        mContext = context;
        mDataset = new ArrayList<>();
        mVisibleDataset = new ArrayList<>();
        mIndexList = new ArrayList<>();
        mExpandMap = new SparseIntArray();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mVisibleDataset != null ? mVisibleDataset.size() : 0;
    }

    protected View inflate(int resourceID, ViewGroup viewGroup) {
        return LayoutInflater.from(mContext).inflate(resourceID,viewGroup,false);
    }
}
