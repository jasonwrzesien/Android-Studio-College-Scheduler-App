package com.example.a2340proj01;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class todoPop extends Activity {
    ArrayList<String> list;
    public int removePosition;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.todopopwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Button addButton = findViewById(R.id.butt_add_todo);
        EditText description = findViewById(R.id.texed_todoDesc);
        ListView itemsView = findViewById(R.id.todo_list_view);

        list = MainActivity.getToDoList();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Delete?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.getToDoList().remove(removePosition);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancels the dialog.
            }
        });

        getWindow().setLayout((int)(width*0.8), (int)(height*0.7));
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        itemsView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!description.getText().toString().equals("")) {
                    MainActivity.getToDoList().add(description.getText().toString());
                    adapter.notifyDataSetChanged();
                    description.setText("");
                }
            }
        });

        itemsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removePosition = position;

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });
    }
}
