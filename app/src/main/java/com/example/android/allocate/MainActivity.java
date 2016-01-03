package com.example.android.allocate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.allocate.db.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivity extends Activity {
    private List<Task> mTasks;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private Button done;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mTasks = new ArrayList<>();

        mTasks.add(new Task(3,"Test1"));
        mTasks.add(new Task(3,"Test2"));
        mTasks.add(new Task(3,"Test3"));
        mTasks.add(new Task(3,"Test4"));
        mTasks.add(new Task(3,"Test5"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        //Enable linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Specifying an adapter
        mAdapter = new TaskAdapter(mTasks);
        mRecyclerView.setAdapter(mAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    public void addTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Add task");
        builder.setMessage("What's your task?");

        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("MainActivity", input.getText().toString());
                mTasks.add(new Task(mTasks.size(), input.getText().toString()));
            }
        });

        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    public void doneTask(View v) {
    }
}


