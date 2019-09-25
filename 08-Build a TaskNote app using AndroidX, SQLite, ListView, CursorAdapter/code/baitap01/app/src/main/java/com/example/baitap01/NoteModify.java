package com.example.baitap01;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

class NoteModify {
    private static NoteModify instance;
    private Context context;
    private SQLiteDatabase db;
    private DBHelper dbHelper;
    private NoteModify(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();

    }
    static NoteModify getInstance(Context context) {
        if(instance == null) {
            instance = new NoteModify(context);
        }
        instance.context = context;
        instance.dbHelper = new DBHelper(context);
        instance.db = instance.dbHelper.getWritableDatabase();
        return instance;
    }
    void insertNode(TaskNote taskNote) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskNoteTable.NoteEntry.COLUMN_CONTENT, taskNote.getContent());
        String strDate = Utilities.dateToString(taskNote.getCreatedDate());
        contentValues.put(TaskNoteTable.NoteEntry.COLUMN_CREATED_DATE, strDate);
        contentValues.put(TaskNoteTable.NoteEntry.COLUMN_IS_IMPORTANT, taskNote.getIsImportant());
        long newRowId = db.insert(TaskNoteTable.NoteEntry.TABLE_NAME, null, contentValues);
        taskNote.setNoteId(newRowId);
    }
    void updateNode(long id, TaskNote taskNote) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String selection = TaskNoteTable.NoteEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        contentValues.put(TaskNoteTable.NoteEntry.COLUMN_CONTENT, taskNote.getContent());
        contentValues.put(TaskNoteTable.NoteEntry.COLUMN_IS_IMPORTANT, taskNote.getIsImportant());
        db.update(TaskNoteTable.NoteEntry.TABLE_NAME, contentValues,
                selection,
                selectionArgs);

    }
    void deleteNode(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = TaskNoteTable.NoteEntry._ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        db.delete(TaskNoteTable.NoteEntry.TABLE_NAME, selection, selectionArgs);
    }

    Cursor getCursorAllNotes() {
        String[] projection = {
                TaskNoteTable.NoteEntry._ID,
                TaskNoteTable.NoteEntry.COLUMN_CONTENT,
                TaskNoteTable.NoteEntry.COLUMN_CREATED_DATE,
                TaskNoteTable.NoteEntry.COLUMN_IS_IMPORTANT
        };
        return db.query(
                TaskNoteTable.NoteEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
    }
}
