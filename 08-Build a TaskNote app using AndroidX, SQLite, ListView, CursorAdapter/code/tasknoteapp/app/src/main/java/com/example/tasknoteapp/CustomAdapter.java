package com.example.tasknoteapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.Date;

public class CustomAdapter extends CursorAdapter {
    public CustomAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtContent = view.findViewById(R.id.txtContent);
        TextView txtCreatedDate = view.findViewById(R.id.txtCreatedDate);
        View viewImportant = view.findViewById(R.id.viewImportant);
        //How to get data from cursor?
        String content = cursor.getString(
                cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_CONTENT));
        boolean isImportant = cursor.getInt(
                cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_IS_IMPORTANT)) > 0;
        String strDate = cursor.getString(
                cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_CREATED_DATE));
        Date createdDate = Utilities.stringToDate(strDate);
        txtContent.setText(content);
        txtCreatedDate.setText(Utilities.dateToString(createdDate));
        viewImportant.setVisibility(isImportant ? View.VISIBLE : View.INVISIBLE);
    }
}
