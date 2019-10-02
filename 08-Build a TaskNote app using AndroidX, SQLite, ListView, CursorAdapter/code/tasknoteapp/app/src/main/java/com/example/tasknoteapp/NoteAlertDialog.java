package com.example.tasknoteapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.Date;

public class NoteAlertDialog extends AlertDialog {
    private View view;
    static int INSERT = 0;
    static int UPDATE = 1;
    int DATA_TYPE = INSERT;
    private Button btnCommit;
    private Button btnCancel;
    private CheckBox checkBoxImportant;
    private EditText txtContent;
    private TaskNote taskNote = new TaskNote();

    NoteAlertDialog(@NonNull Context context) {
        super(context);
        setupUI();
    }
    NoteAlertDialog(@NonNull Context context, TaskNote taskNote, int dataType) {
        super(context);
        this.taskNote = taskNote;
        this.DATA_TYPE = dataType;
        setupUI();
    }
    private void setupUI() {
        view = this.getLayoutInflater().inflate(R.layout.note_alert_dialog, null);
        btnCommit = view.findViewById(R.id.btnCommit);
        btnCancel = view.findViewById(R.id.btnCancel);
        checkBoxImportant = view.findViewById(R.id.checkBoxImportant);
        txtContent = view.findViewById(R.id.txtName);
        btnCancel.setOnClickListener(v -> {
            this.dismiss();
        });
        btnCommit.setOnClickListener(v->{
            String content = txtContent.getText().toString().trim();
            Boolean isImportant = checkBoxImportant.isChecked();
            if(content.equals("")) {
                this.dismiss();
                return;
            }
            if(this.DATA_TYPE == NoteAlertDialog.INSERT) {
                TaskNote newTaskNote = new TaskNote(-1, content, isImportant, new Date());
                NoteModify.getInstance(this.getContext()).insertNote(newTaskNote);
            } else if(this.DATA_TYPE == NoteAlertDialog.UPDATE) {
                taskNote.setContent(content);
                taskNote.setImportant(isImportant);
                NoteModify.getInstance(this.getContext()).updateNote(taskNote.getNodeId(), taskNote);
            }
            //refresh MainActivity
            MainActivity mainActivity = (MainActivity) ((ContextWrapper)getContext()).getBaseContext();
            mainActivity.refreshListView();
            this.dismiss();
        });
        if(this.DATA_TYPE == NoteAlertDialog.UPDATE){
            fetchDataToUI();
        }
        this.setView(view);
    }
    private void fetchDataToUI() {
        if(taskNote.getContent().trim().length() >0){
            txtContent.setText(taskNote.getContent());
            checkBoxImportant.setChecked(taskNote.getImportant());
        }
    }
}
