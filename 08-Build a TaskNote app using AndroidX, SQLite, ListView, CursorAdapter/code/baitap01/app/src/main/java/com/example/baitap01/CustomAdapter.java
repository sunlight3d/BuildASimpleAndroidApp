package com.example.baitap01;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import java.util.Date;

public class CustomAdapter extends CursorAdapter {
    CustomAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_tem, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtCreatedDate = view.findViewById(R.id.txtCreatedDate);
        View viewLeft = view.findViewById(R.id.viewLeft);
        String content = cursor.getString(
                cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_CONTENT));

        boolean isImportant = cursor.getInt(
                cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_IS_IMPORTANT)) > 0;

        String strDate = cursor.getString(
                cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_CREATED_DATE));
        Date createdDate = Utilities.stringToDate(strDate);

        txtName.setText(content);
        txtCreatedDate.setText(Utilities.dateToString(createdDate));
        viewLeft.setBackgroundColor(isImportant ? Color.RED : Color.WHITE);
    }
}
