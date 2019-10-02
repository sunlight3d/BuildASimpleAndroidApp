package com.example.tasknoteapp;

import android.database.Cursor;

import java.util.Date;

public class TaskNote {
    private long nodeId = -1;
    private String content;
    private Boolean isImportant;
    private Date createdDate;
    public TaskNote(){
        content = "";
        isImportant = false;
        createdDate = new Date();
    }
    public TaskNote(long nodeId, String content, Boolean isImportant, Date createdDate) {
        this.nodeId = nodeId;
        this.content = content;
        this.isImportant = isImportant;
        this.createdDate = createdDate;
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getImportant() {
        return isImportant;
    }

    public void setImportant(Boolean important) {
        isImportant = important;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    public static TaskNote getNoteFromCursor(Cursor cursor) {
        //"factory method"
        try {
            String content = cursor.getString(
                    cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_CONTENT));
            Boolean isImportant = cursor.getInt(
                    cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_IS_IMPORTANT)) > 0;
            long dateLong = cursor.getLong(
                    cursor.getColumnIndexOrThrow(TaskNoteTable.NoteEntry.COLUMN_CREATED_DATE));
            long noteId =  cursor.getLong(cursor.getColumnIndex(TaskNoteTable.NoteEntry._ID));
            Date createdDate = new Date(dateLong);
            return new TaskNote(noteId, content,isImportant, createdDate);
        }catch (Exception e) {
            System.out.println("Cannot create TaskNote. Error: "+e.toString());
            return null;
        }
    }
}

