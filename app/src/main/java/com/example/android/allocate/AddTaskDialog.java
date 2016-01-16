package com.example.android.allocate;

import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dooj on 2016-01-05
 */
public class AddTaskDialog extends DialogFragment {
    private List<ImageButton> mNumPad;
    private TextView mTimeTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
