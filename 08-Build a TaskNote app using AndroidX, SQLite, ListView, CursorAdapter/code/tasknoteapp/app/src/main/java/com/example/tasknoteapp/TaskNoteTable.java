package com.example.tasknoteapp;

import android.provider.BaseColumns;

public final class TaskNoteTable {
    static class NoteEntry implements BaseColumns {
        static final String TABLE_NAME = "tblTaskNote";
        static final String COLUMN_CONTENT = "content";
        static final String COLUMN_IS_IMPORTANT = "isImportant";
        static final String COLUMN_CREATED_DATE = "createdDate";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                        NoteEntry._ID + " INTEGER PRIMARY KEY," +
                        NoteEntry.COLUMN_CONTENT + " TEXT," +
                        NoteEntry.COLUMN_CREATED_DATE + " TEXT," +
                        NoteEntry.COLUMN_IS_IMPORTANT + " INT)";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;
    }
}
