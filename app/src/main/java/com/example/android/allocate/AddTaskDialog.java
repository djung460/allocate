package com.example.android.allocate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        builder.setView(layoutInflater.inflate(R.layout.set_timer_layout,null));
        return super.onCreateDialog(savedInstanceState);
    }
}
