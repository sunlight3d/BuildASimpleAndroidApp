package com.example.baitap01;

import android.database.Cursor;

import java.util.Date;

class TaskNote {
    private long noteId = -1;
    private String content;
    private Boolean isImportant;
    private Date createdDate;
    TaskNote(){
        content = "";
        isImportant = false;
        createdDate = new Date();
    }

    TaskNote(long noteId, String content, Boolean isImportant, Date createdDate) {
        this.noteId = noteId;
        this.content = content;
        this.isImportant = isImportant;
        this.createdDate = createdDate;
    }

    String getContent() {
        return content;
    }

    void setContent(String content) {
        this.content = content;
    }

    Boolean getIsImportant() {
        return isImportant;
    }

    void setIsImportant(Boolean isImportant) {
        this.isImportant = isImportant;
    }

    Date getCreatedDate() {
        return createdDate;
    }

    static TaskNote getNoteFromCursor(Cursor cursor) {
        try {

            String content = cursor.getString(
                    cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_CONTENT));
            Boolean isImportant = cursor.getInt(
                    cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_IS_IMPORTANT)) > 0;
            long dateLong = cursor.getLong(
                    cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_CREATED_DATE));
            long noteId =  cursor.getLong(cursor.getColumnIndex(TaskNoteTable.NoteEntry._ID));
            Date createdDate = new Date(dateLong);
            return new TaskNote(noteId, content, isImportant, createdDate);
        }catch (Exception e) {
            System.out.println("Cannot create TaskNote. Error:"+e.toString());
            return null;
        }

    }

    long getNoteId() {
        return noteId;
    }

    void setNoteId(long noteId) {
        this.noteId = noteId;
    }
}
