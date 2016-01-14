package com.example.android.allocate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TimePicker;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.example.android.allocate.db.TaskDatabaseHelper;
import com.example.android.allocate.db.TaskHandler;
import com.example.android.allocate.task.Task;

/**
 * Created by Dooj on 2016-01-03.
 */
public class AddTaskActivity extends AppCompatActivity {
//    private TaskDatabaseHelper mDbHelper;
    private TaskHandler mTaskHandler;
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextHmsInput;

    private static final int HOURS_TO_MILLIS = 3600000;
    private static final int MINUTES_TO_MILLIS = 60000;
    private static final int SECONDS_TO_MILLIS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
        mTaskHandler = new TaskHandler(this);
        //mDbHelper = new TaskDatabaseHelper(this);

        editTextTitle = (EditText) findViewById(R.id.task_name);
        editTextDescription = (EditText) findViewById(R.id.task_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.task_bar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Add Task");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextHmsInput = (EditText) findViewById(R.id.hms_field);

        Intent intent = getIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle app bar item clicks here. The app bar
        // automatically handles clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_done) {
            addTask();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addTask() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String hms_input=editTextHmsInput.getText().toString();

        int hours = Integer.parseInt(hms_input.substring(0, 2));
        int minutes = Integer.parseInt(hms_input.substring(2, 4));
        int seconds = Integer.parseInt(hms_input.substring(4,6));

        long timeInitial = hours*HOURS_TO_MILLIS + minutes*MINUTES_TO_MILLIS + seconds*SECONDS_TO_MILLIS;
        Task task = new Task(title.hashCode() + description.hashCode(), title, description, false, timeInitial);
        mTaskHandler.addTask(task);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.add_task,
                    container, false);
            return rootView;
        }
    }
}
