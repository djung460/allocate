package com.example.android.allocate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.example.android.allocate.db.TaskDatabaseHelper;
import com.example.android.allocate.db.TaskHandler;
import com.example.android.allocate.task.Task;

import java.util.List;

/**
 * Created by Dooj on 2016-01-03.
 */

//TODO IMPLEMENT THIS CLASS WITH UPDATED INPUT ACTIVITY
public class AddTaskActivity extends AppCompatActivity {
    //    private TaskDatabaseHelper mDbHelper;
    private TaskHandler mTaskHandler;
    private EditText editTextTitle;
    private EditText editTextDescription;

    private TextView textViewHrs;
    private TextView textViewMins;
    private TextView textViewSecs;

    private ImageButton backspace;
    private Layout setTimerLayout;
    private int cursor;

    StringBuilder hms_time;

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
        getSupportActionBar().setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        textViewHrs = (TextView) findViewById(R.id.text_view_hrs);
        textViewMins = (TextView) findViewById(R.id.text_view_mins);
        textViewSecs = (TextView) findViewById(R.id.text_view_secs);

        backspace = (ImageButton) findViewById(R.id.image_button_backspace);

        hms_time = new StringBuilder();
        hms_time.append("000000");

        cursor = 0;
    }

    public void enterNum(View v) {
        if (cursor < 6) {
            switch (v.getId()) {
                case R.id.numpad_0:
                    hms_time.append('0');
                    setTimeText();
                    break;
                case R.id.numpad_1:
                    hms_time.append('1');
                    setTimeText();
                    break;
                case R.id.numpad_2:
                    hms_time.append('2');
                    setTimeText();
                    break;
                case R.id.numpad_3:
                    hms_time.append('3');
                    setTimeText();
                    break;
                case R.id.numpad_4:
                    hms_time.append('4');
                    setTimeText();
                    break;
                case R.id.numpad_5:
                    hms_time.append('5');
                    setTimeText();
                    break;
                case R.id.numpad_6:
                    hms_time.append('6');
                    setTimeText();
                    break;
                case R.id.numpad_7:
                    hms_time.append('7');
                    setTimeText();
                    break;
                case R.id.numpad_8:
                    hms_time.append('8');
                    setTimeText();
                    break;
                case R.id.numpad_9:
                    hms_time.append('9');
                    setTimeText();
                    break;
                case R.id.image_button_backspace:
                    hms_time.deleteCharAt(hms_time.length() - 1);
                    cursor -= 2;
                    setTimeText();
                    break;
            }
        }
    }

    public void setTimeText() {
        cursor++;
        textViewHrs.setText(hms_time.substring(cursor, 2 + cursor));
        textViewMins.setText(hms_time.substring(2 + cursor, 4 + cursor));
        textViewSecs.setText(hms_time.substring(4 + cursor, 6 + cursor));
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

        int hours = Integer.parseInt(hms_time.substring(cursor, 2 + cursor));
        int minutes = Integer.parseInt(hms_time.substring(2 + cursor, 4 + cursor));
        int seconds = Integer.parseInt(hms_time.substring(4 + cursor, 6 + cursor));

        long timeInitial = hours * HOURS_TO_MILLIS + minutes * MINUTES_TO_MILLIS + seconds * SECONDS_TO_MILLIS;
        Task task = new Task(title.hashCode() + description.hashCode(), title, description, false, timeInitial, timeInitial);
        mTaskHandler.addTask(task);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.add_task,
                    container, false);
            return rootView;
        }
    }
}
