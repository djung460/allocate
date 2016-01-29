package com.example.android.allocate.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.allocate.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David Jung on 2015-12-25.
 *
 * Acts as an interface with the SQLite database. The task information is written each time
 * the main activity is paused and read each time the app resumes.
 */
public class TaskDatabaseHelper extends SQLiteOpenHelper{
    private static final String INT_TYPE = " INTEGER";
    private static final String TEXT_TYPE = " TEXT";
    private static final String BOOLEAN_TYPE = " BOOLEAN";
    private static final String COMMA_SEP = ",";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " (" +
                    TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY," +
                    TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID + INT_TYPE + COMMA_SEP +
                    TaskContract.TaskEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    TaskContract.TaskEntry.COLUMN_NAME_STATUS + BOOLEAN_TYPE + COMMA_SEP +
                    TaskContract.TaskEntry.COLUMN_NAME_TIMELEFT + INT_TYPE + COMMA_SEP +
                    TaskContract.TaskEntry.COLUMN_NAME_INITIALTIME + INT_TYPE + COMMA_SEP +
                    TaskContract.TaskEntry.COLUMN_NAME_TICKS + INT_TYPE +
                    ")";

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "Task.db";

    public TaskDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Generates an sql database
     *
     * @param db to be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("TaskDBHelper", "Query to form table: " + SQL_CREATE_ENTRIES);

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID, task.getId());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_STATUS,task.isRunning());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE,task.getTitle());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_TIMELEFT, task.getTimeLeft());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_INITIALTIME, task.getInitialTime());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_TICKS, task.numTicks());

        long newRowId;
        newRowId = db.insert(
                TaskContract.TaskEntry.TABLE_NAME,
                null,
                contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "select * from contacts where TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID =" +
                id + ""
                ,null
        );
        return res;
    }

    public int numberOfEntries() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numEntries = (int) DatabaseUtils.queryNumEntries(db, TaskContract.TaskEntry.TABLE_NAME);
        return numEntries;
    }

    public boolean updateTask (Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID, task.getId());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_STATUS,task.isRunning());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_TITLE,task.getTitle());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_TIMELEFT, task.getTimeLeft());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_INITIALTIME, task.getInitialTime());
        contentValues.put(TaskContract.TaskEntry.COLUMN_NAME_TICKS, task.numTicks());

        db.update(
                TaskContract.TaskEntry.TABLE_NAME,
                contentValues,
                TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID + " = ? ",
                new String[] { Long.toString(task.getId()) } );
        return true;
    }

    public void updateTable(List<Task> dataset) {
        for(Task t : dataset){
            updateTask(t);
        }
    }

    public Integer deleteTask(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                TaskContract.TaskEntry.TABLE_NAME,
                TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID + " = ? ",
                new String[] { Long.toString(id) } );
    }

    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TaskContract.TaskEntry.TABLE_NAME,null);
        res.moveToFirst();

        while(!res.isAfterLast() && numberOfEntries() != 0) {
            taskArrayList.add(0,new Task(
                    res.getLong(res.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_ENTRY_ID)),
                    res.getString(res.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_TITLE)),
                    res.getInt(res.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_STATUS)) != 0,
                    res.getLong(res.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_TIMELEFT)),
                    res.getLong(res.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_INITIALTIME)),
                    res.getInt(res.getColumnIndex(TaskContract.TaskEntry.COLUMN_NAME_TICKS))
                    )
            );

            res.moveToNext();
        }

        return taskArrayList;
    }

    public void clearTasks() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TaskContract.TaskEntry.TABLE_NAME);
    }
}
