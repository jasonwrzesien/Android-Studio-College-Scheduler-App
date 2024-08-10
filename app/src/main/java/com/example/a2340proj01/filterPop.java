package com.example.a2340proj01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class filterPop extends Activity {

    CheckBox toggleClass;
    CheckBox toggleExam;
    CheckBox toggleAssignment;
    Button applySettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filterpopwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * 0.85), (int) (height * 0.7));

        // Get references to elements
        toggleClass = findViewById(R.id.classToggle);
        toggleExam = findViewById(R.id.examToggle);
        toggleAssignment = findViewById(R.id.assignmentToggle);
        applySettings = findViewById(R.id.applyButton);

        // Set toggles to previous positions
        // Get Intent
        Intent i = getIntent();
        Bundle extras = i.getExtras();

        // Set toggles based on extras
        toggleClass.setChecked(extras.getBoolean("classViz"));
        toggleExam.setChecked(extras.getBoolean("examViz"));
        toggleAssignment.setChecked(extras.getBoolean("assignViz"));


        applySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent extras = new Intent();

        extras.putExtra("classViz", toggleClass.isChecked());
        extras.putExtra("examViz", toggleExam.isChecked());
        extras.putExtra("assignViz", toggleAssignment.isChecked());

        setResult(RESULT_OK, extras);
        super.finish();
    }
}
