package com.example.baitap01;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.Date;
public class NoteAlertDialog extends AlertDialog {
    private View view;
    static int INSERT = 0;
    static int UPDATE = 2;
    int DATA_TYPE = INSERT;
    private Button btnCommit;
    private Button btnCancel;
    private CheckBox checkBoxImportant;
    private EditText txtName;
    private TaskNote taskNote = new TaskNote();
    NoteAlertDialog(@NonNull Context context) {
        super(context);
        setupUI();
    }
    NoteAlertDialog(@NonNull Context context, TaskNote taskNote) {
        super(context);
        this.setTaskNote(taskNote);
        this.taskNote = taskNote;
        setupUI();
    }


    void setTaskNote(TaskNote taskNote) {
        fetchDataToUI();
        this.taskNote = taskNote;
    }
    private void setupUI(){
        this.view = this.getLayoutInflater().inflate(R.layout.node_alert_dialog, null);
        btnCommit = view.findViewById(R.id.btnCommit);
        btnCancel = view.findViewById(R.id.btnCancel);
        checkBoxImportant = view.findViewById(R.id.checkBoxImportant);
        txtName = view.findViewById(R.id.txtName);
        btnCancel.setOnClickListener(v -> {
            this.dismiss();
        });
        btnCommit.setOnClickListener(v -> {
            String content = txtName.getText().toString().trim();
            Boolean isImportant = checkBoxImportant.isChecked();
            if(content.equals("")) {
                //show alert
            }
            TaskNote newTaskNote = new TaskNote(-1, content,
                    isImportant,
                    new Date());
            if(DATA_TYPE == INSERT) {
                NoteModify.getInstance(this.getContext()).insertNode(newTaskNote);
            } else {
                taskNote.setContent(txtName.getText().toString().trim());
                taskNote.setIsImportant(checkBoxImportant.isChecked());
                NoteModify.getInstance(this.getContext()).updateNode(taskNote.getNoteId(), taskNote);
            }
            MainActivity mainActivity = (MainActivity)(((ContextWrapper)getContext()).getBaseContext());
            mainActivity.refreshListView();
            this.dismiss();
        });
        fetchDataToUI();
        setView(view);
    }

    private void fetchDataToUI(){
        if(taskNote.getContent().trim().length() > 0) {
            txtName.setText(taskNote.getContent());
            checkBoxImportant.setChecked(taskNote.getIsImportant());
        }
    }
}
