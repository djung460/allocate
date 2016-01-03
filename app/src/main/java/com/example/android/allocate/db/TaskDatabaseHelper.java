package com.example.android.allocate.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dooj on 2015-12-25.
 */
public class TaskDatabaseHelper extends SQLiteOpenHelper{
    public TaskDatabaseHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    /**
     * Generates an sql database
     *
     * @param db to be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery =
                String.format("CREATE TABLE %s(" +
                        "%s INT PRIMARY KEY NOT NULL" +
                        "%s TEXT NOT NULL" +
                        "%s BOOLEAN NOT NULL"
                        , TaskContract.DB_NAME
                        , TaskContract.Columns.KEY_ID
                        , TaskContract.Columns.KEY_TASKNAME
                        , TaskContract.Columns.KEY_STATUS
                );

        Log.d("TaskDBHelper", "Query to form table: " + sqlQuery);

        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE);
        onCreate(db);
    }
}
