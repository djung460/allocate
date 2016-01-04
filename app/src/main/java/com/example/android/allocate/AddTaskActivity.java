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

import com.example.android.allocate.db.TaskDatabaseHelper;

/**
 * Created by Dooj on 2016-01-03.
 */
public class AddTaskActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TaskDatabaseHelper mDbHelper;
    private EditText editTextTitle;
    private EditText editTextDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        mDbHelper = new TaskDatabaseHelper(this);

        editTextTitle = (EditText) findViewById(R.id.task_name);
        editTextDescription = (EditText) findViewById(R.id.task_description);

        toolbar = (Toolbar) findViewById(R.id.task_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        mDbHelper.insertTask(title.hashCode() + description.hashCode(), title, description ,false );
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
