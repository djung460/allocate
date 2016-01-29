package com.example.android.allocate.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.allocate.R;
import com.example.android.allocate.TickReciever;
import com.example.android.allocate.TimerBroadcastService;
import com.example.android.allocate.alarms.TimerDoneReceiver;
import com.example.android.allocate.db.TaskHandler;
import com.example.android.allocate.task.Task;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivity extends AppCompatActivity {
    //Member variables for recycler view displays info
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    //Extra Inputs
    private FloatingActionButton fab;
    private Toolbar toolbar;

    private TaskHandler mTaskHandler;
    private TickReciever mBroadcastReceiver;

    private TimerDoneReceiver timerDoneReceiver = new TimerDoneReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTaskHandler = new TaskHandler(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Allocate");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mTaskHandler.getTaskAdapter());

        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddTaskActivity();
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (mTaskHandler.getDataset().get(viewHolder.getAdapterPosition()).isRunning())
                    return;
                else {
                    mTaskHandler.getTaskAdapter().deleteItem(viewHolder.getAdapterPosition());
                    Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                mTaskHandler.getTaskAdapter().onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }


        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Start timer
        startService(new Intent(this, TimerBroadcastService.class));

        mBroadcastReceiver = new TickReciever();
        mBroadcastReceiver.initialize(mTaskHandler);

        for (Task t : mTaskHandler.getDataset()) {
            if (t.isRunning()) {
                timerDoneReceiver.cancelAlarms(this);
            }
        }

        Log.i(TimerBroadcastService.COUNTDOWN_BROADCAST, "Started service");
        registerReceiver(mBroadcastReceiver, new IntentFilter(TimerBroadcastService.COUNTDOWN_BROADCAST));
        mTaskHandler.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Start timer
        stopService(new Intent(this, TimerBroadcastService.class));
        Log.i(TimerBroadcastService.COUNTDOWN_BROADCAST, "Stopped service");

        for (Task t : mTaskHandler.getDataset()) {
            if (t.isRunning()) {
                timerDoneReceiver.setAlarms(this, t.getTimeLeft(), t.getId());
            }
        }

        mTaskHandler.pause();
    }

    @Override
    public void onStop() {
        unregisterReceiver(mBroadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_clear:
                mTaskHandler.clearTask();
                Toast.makeText(MainActivity.this, "Task Cleared", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAddTaskActivity() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }
}


