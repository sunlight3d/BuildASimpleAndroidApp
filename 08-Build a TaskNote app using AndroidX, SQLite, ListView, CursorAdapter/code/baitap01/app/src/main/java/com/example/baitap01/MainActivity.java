package com.example.baitap01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private CustomAdapter customAdapter;
    private Cursor mCursor;
    private int selectedPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);
        mCursor =  NoteModify.getInstance(this).getCursorAllNotes();
        customAdapter = new CustomAdapter(this, mCursor);
        listView.setAdapter(customAdapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("TaskNote Management");
        this.setSupportActionBar(toolbar);

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            selectedPosition = position;
        });
        registerForContextMenu(listView);
    }
    public void refreshListView() {
        mCursor =  NoteModify.getInstance(this).getCursorAllNotes();
        customAdapter.swapCursor(mCursor);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemMenu = item.getItemId();
        switch (itemMenu){
            case R.id.itemAdd:
                Toast.makeText(this,"Bam add", Toast.LENGTH_LONG).show();
                NoteAlertDialog noteAlertDialog = new NoteAlertDialog(this);
                refreshListView();
                noteAlertDialog.show();
                break;
            case R.id.itemExit:
                Toast.makeText(this,"Bam exit", Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }
    //Context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Cursor cursor = (Cursor) customAdapter.getItem(selectedPosition);
                TaskNote selectedTaskNote = TaskNote.getNoteFromCursor(cursor);
                NoteAlertDialog noteAlertDialog = new NoteAlertDialog(MainActivity.this, selectedTaskNote);
                noteAlertDialog.DATA_TYPE = NoteAlertDialog.UPDATE;
                noteAlertDialog.show();
                break;
            case R.id.delete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete TaskNote")
                        .setMessage("Do you really want to delete this?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, (DialogInterface dialog, int whichButton) -> {
                            Cursor selectedCursor = (Cursor) customAdapter.getItem(selectedPosition);
                            if(selectedCursor !=null) {
                                TaskNote taskNote = TaskNote.getNoteFromCursor(selectedCursor);
                                if(taskNote != null) {
                                    NoteModify.getInstance(MainActivity.this).deleteNode(taskNote.getNoteId());
                                    refreshListView();
                                }
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

                break;
        }
        return super.onContextItemSelected(item);
    }

}
