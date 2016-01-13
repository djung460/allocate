package com.example.android.allocate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.example.android.allocate.db.TaskDatabaseHelper;
import com.example.android.allocate.task.ExpandedTask;
import com.example.android.allocate.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivity extends AppCompatActivity {
    //Tasks
    private List<Task> mTasks;

    //Database
    private TaskDatabaseHelper mDbHelper;

    //Member variables for recycler view displays info
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mTaskAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Extra Inputs
    private FloatingActionButton fab;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        mDbHelper = new TaskDatabaseHelper(this);
        mTasks = mDbHelper.getAllTasks();

        toolbar = (Toolbar) findViewById(R.id.main_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Allocate");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mTaskAdapter = new TaskAdapter(mTasks);
        mRecyclerView.setAdapter(mTaskAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddTaskActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_settings :
                break;
            case R.id.action_clear :
                mDbHelper.clearTasks();
                refresh();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refresh() {
        mTasks.clear();
        mTasks.addAll(mDbHelper.getAllTasks());
        mTaskAdapter.notifyDataSetChanged();
    }

    private void doneTask(View v) {
        refresh();
    }

    public void startAddTaskActivity() {
        Intent intent  = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }
}


