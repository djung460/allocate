package com.example.android.allocate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.design.widget.SwipeDismissBehavior;
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

import com.example.android.allocate.db.TaskDatabaseHelper;
import com.example.android.allocate.db.TaskHandler;
import com.example.android.allocate.task.Task;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

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

        //Start timer
        startService(new Intent(this, TimerBroadcastService.class));
        Log.i(TimerBroadcastService.COUNTDOWN_BROADCAST, "Started service");

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                mTaskHandler.getTaskAdapter().deleteItem(viewHolder.getAdapterPosition());
                Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mTaskHandler.tick();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, new IntentFilter(TimerBroadcastService.COUNTDOWN_BROADCAST));
        mTaskHandler.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
        mTaskHandler.pause();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, TimerBroadcastService.class));
        Log.i(TimerBroadcastService.COUNTDOWN_BROADCAST, "Stopped service");
        super.onDestroy();
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(mBroadcastReceiver);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
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
                mTaskHandler.clearTask();
                Toast.makeText(MainActivity.this, "Task Cleared", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startAddTaskActivity() {
        Intent intent  = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }
}


