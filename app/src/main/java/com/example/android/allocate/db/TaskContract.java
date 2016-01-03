package com.example.android.allocate.db;

import android.provider.BaseColumns;

/**
 * Created by Dooj on 2015-12-30.
 */
public class TaskContract {
    public static final String DB_NAME = "com.example.allocate.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";


    public class Columns {
        public static final String KEY_ID = "ID";
        public static final String KEY_TASKNAME = "NAME";
        public static final String KEY_STATUS = "STATUS";
    }
}
