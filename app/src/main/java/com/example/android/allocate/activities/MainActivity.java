package com.example.android.allocate.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.allocate.R;
import com.example.android.allocate.TickReciever;
import com.example.android.allocate.alarms.TimerDoneReceiver;
import com.example.android.allocate.db.TaskHandler;
import com.example.android.allocate.task.Task;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Member variables for recycler view displays info
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    //Extra Inputs
    private FloatingActionButton fab;
    private Toolbar toolbar;

    private TaskHandler mTaskHandler;
    private TickReciever mBroadcastReceiver;

    private TimerDoneReceiver timerDoneReceiver = new TimerDoneReceiver();

    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTaskHandler.tick();

            handler.postDelayed(this, 100);
        }
    };

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

        // Floating action button for adding a new task
        fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(this);

        // Detects swipe in the Android recycler view
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (!mTaskHandler.getDataset().get(viewHolder.getAdapterPosition()).isRunning()){
                    mTaskHandler.getTaskAdapter().deleteItem(viewHolder.getAdapterPosition());
                    Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        handler.postDelayed(runnable, 100);
        timerDoneReceiver.cancelAlarms();
        mTaskHandler.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        handler.removeCallbacks(runnable);

        for (Task t : mTaskHandler.getDataset()) {
            if (t.isRunning()) {
                timerDoneReceiver.setAlarms(this, t.getTimeLeft(), t.getId(), t.getTitle());
            }
        }

        mTaskHandler.pause();
    }

    @Override
    public void onStop() {
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

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fab_add:
                startAddTaskActivity();
                break;
            case R.id.view_stats:

        }
    }

    private void startAddTaskActivity() {
        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
    }

    private void startViewStatsActivity() {
        Intent intent = new Intent(this, ViewStatsActivity.class);
        startActivity(intent);
    }
}


