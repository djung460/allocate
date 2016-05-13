package com.example.android.allocate.db;

import android.provider.BaseColumns;

/**
 * Created by David Jung on 2015-12-30.
 */
public final class TaskContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public TaskContract() {}

    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryID";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_TIMELEFT = "timeLeft";
        public static final String COLUMN_NAME_INITIALTIME = "initialTime";
        public static final String COLUMN_NAME_TICKS = "ticks";
    }
}
