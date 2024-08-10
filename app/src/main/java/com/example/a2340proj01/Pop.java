package com.example.a2340proj01;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.chip.Chip;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ArrayList;

public class Pop extends Activity {

    // For Date Picker
    private Button pickStartDateButt;
    private TextView selectedStartDateTV;
    private Button pickEndDateButt;
    private TextView selectedEndDateTV;
    // Finish Butts
    private Button addItemButt;
    private Button saveButt;
    private Button deleteButt;

    //Dynamic Text Properties/Labels
    private TextView textview_newItemType;
    private Button butt_startDate;

    // Input State Management for Pop
            // 0 Type_to_add selection
        // Input States
            // 1 class, 2 exam, 3 assignment, += 20 edit of type
        // Finish States
            // +=10 output of added type, +=30 output of edit type, += 40 delete of type
    private int inputType = 0;

    // Id Extra for Editing and Deleting
    private int sourceId = -1;

    private Button addClassButt;
    private Button addExamButt;
    private Button addAssignmentButt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Popup Menu Setup
        setContentView(R.layout.popwindow);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        // Button and TextView for StartDatePick
        pickStartDateButt = findViewById(R.id.butt_startDate);
        selectedStartDateTV = findViewById(R.id.textview_startDate);
        // Button and TextView for EndDatePick
        pickEndDateButt = findViewById(R.id.butt_endDate);
        selectedEndDateTV = findViewById(R.id.textview_endDate);

        // InputState Buttons
        addClassButt = findViewById(R.id.butt_addClass);
        addExamButt = findViewById(R.id.butt_addExam);
        addAssignmentButt = findViewById(R.id.butt_addAssignment);
        // Finish Buttons
        addItemButt = findViewById(R.id.butt_addToCalendar);
        saveButt = findViewById(R.id.butt_save);
        deleteButt = findViewById(R.id.butt_delete);

        //Dynamic Text Properties/Labels
        textview_newItemType = findViewById(R.id.textview_newItemType);
        butt_startDate = findViewById(R.id.butt_startDate);

        // Display
        getWindow().setLayout((int) (width * 0.85), (int) (height * 0.7));

        // Get State and SourceID from Intent if Editing
        Bundle extras = getIntent().getExtras();
        if ( extras != null) {
            inputType = extras.getInt("state");
            sourceId = extras.getInt("sourceID");
        }

        // UI Items
        updateInput();

        // Input State Buttons
        addClassButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputType = 1;
                updateInput();
            }
        });
        addExamButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputType = 2;
                updateInput();
            }
        });
        addAssignmentButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputType = 3;
                updateInput();
            }
        });

        // StartDatePicker
        pickStartDateButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // Passing Context
                        Pop.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                int m = monthOfYear + 1;
                                int d = dayOfMonth;
                                String month = String.valueOf(m);
                                String day = String.valueOf(d);
                                if (m < 9) {
                                    month = "0" + String.valueOf(m);
                                }
                                if (d < 10) {
                                    day = "0" + String.valueOf(d);
                                }
                                selectedStartDateTV.setText(month + "-" + day + "-" + year);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        // EndDatePicker
        pickEndDateButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // Passing Context
                        Pop.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                int m = monthOfYear + 1;
                                int d = dayOfMonth;
                                String month = String.valueOf(m);
                                String day = String.valueOf(d);
                                if (m < 9) {
                                    month = "0" + String.valueOf(m);
                                }
                                if (d < 10) {
                                    day = "0" + String.valueOf(d);
                                }
                                selectedEndDateTV.setText(month + "-" + day + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        // Click - Delete Item  ===================================================
        // ========================================================================
        deleteButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputType += 20;
                finish();
            }
        });

        // Click - Save Item Edit =================================================
        // ========================================================================
        saveButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set up bool to check if data is correct
                boolean dataCorrect = false;

                // Get textviews
                TextView texed_courseName = findViewById(R.id.texed_courseName);
                TextView texed_time = findViewById(R.id.texed_time);
                TextView textview_startDate = findViewById(R.id.textview_startDate);

                // Check each case based on inputType
                if (inputType == 21) {

                    // Get Class specific items
                    Chip chip_sun = findViewById(R.id.chip_sun);
                    Chip chip_mon = findViewById(R.id.chip_mon);
                    Chip chip_tues = findViewById(R.id.chip_tues);
                    Chip chip_wed = findViewById(R.id.chip_wed);
                    Chip chip_thurs = findViewById(R.id.chip_thurs);
                    Chip chip_fri = findViewById(R.id.chip_fri);
                    Chip chip_sat = findViewById(R.id.chip_sat);
                    TextView texed_instructor = findViewById(R.id.texed_instructor);
                    TextView textview_endDate = findViewById(R.id.textview_endDate);
                    TextView texed_length = findViewById(R.id.texed_length);
                    TextView texed_location = findViewById(R.id.texed_location);

                    if (!texed_courseName.getText().toString().isEmpty() &&
                            !texed_time.getText().toString().isEmpty() &&
                            !textview_startDate.getText().toString().equals("Date") &&
                            !texed_instructor.getText().toString().isEmpty() &&
                            !textview_endDate.getText().toString().equals("Date") &&
                            !texed_length.getText().toString().isEmpty() &&
                            !texed_location.getText().toString().isEmpty()) {

                        // Ensure that at least one day is toggled
                        int numChecked = 0;
                        if (chip_sun.isChecked())
                            numChecked++;
                        if (chip_mon.isChecked())
                            numChecked++;
                        if (chip_tues.isChecked())
                            numChecked++;
                        if (chip_wed.isChecked())
                            numChecked++;
                        if (chip_thurs.isChecked())
                            numChecked++;
                        if (chip_fri.isChecked())
                            numChecked++;
                        if (chip_sat.isChecked())
                            numChecked++;

                        // Check if start/end date is correct
                        boolean dateValid = false;
                        int startMonth = Integer.parseInt(textview_startDate.getText().toString().substring(0, 2));
                        int startDay = Integer.parseInt(textview_startDate.getText().toString().substring(3, 5));
                        int startYear = Integer.parseInt(textview_startDate.getText().toString().substring(6));

                        int endMonth = Integer.parseInt(textview_endDate.getText().toString().substring(0, 2));
                        int endDay = Integer.parseInt(textview_endDate.getText().toString().substring(3, 5));
                        int endYear = Integer.parseInt(textview_endDate.getText().toString().substring(6));

                        if (endYear - startYear > 0) {
                            dateValid = true;
                        } else if (endYear - startYear == 0 && endMonth - startMonth > 0) {
                            dateValid = true;
                        } else if (endYear - startYear == 0 && endMonth - startMonth == 0 && endDay - startDay > 0) {
                            dateValid = true;
                        }

                        // Check if time is valid format
                        boolean timeValid = false;
                        String time = texed_time.getText().toString();
                        if (time.length() == 5) {
                            if ((Integer.parseInt(time.substring(0, 1))) < 2 || (Integer.parseInt(time.substring(0, 1)) == 2 && Integer.parseInt(time.substring(1, 2)) <= 4)) {
                                if (time.substring(2, 3).equals(":") && Integer.parseInt(time.substring(3, 5)) < 60) {
                                    timeValid = true;
                                }
                            }
                        }

                        // Check if length is valid format
                        boolean lengthValid = false;
                        String length = texed_length.getText().toString();
                        if (length.length() == 5) {
                            if ((Integer.parseInt(length.substring(0, 1))) < 2 || (Integer.parseInt(length.substring(0, 1)) == 2 && Integer.parseInt(length.substring(1, 2)) <= 4)) {
                                if (length.substring(2, 3).equals(":") && Integer.parseInt(length.substring(3, 5)) < 60) {
                                    lengthValid = true;
                                }
                            }
                        }

                        if (dateValid && timeValid && lengthValid && numChecked > 0) {
                            dataCorrect = true;
                        }
                    }
                } else if (inputType == 22) {
                    // Get exam specific items
                    TextView texed_length = findViewById(R.id.texed_length);
                    TextView texed_location = findViewById(R.id.texed_location);

                    if (!texed_courseName.getText().toString().isEmpty() &&
                            !texed_time.getText().toString().isEmpty() &&
                            !texed_location.getText().toString().isEmpty() &&
                            !texed_length.getText().toString().isEmpty() &&
                            !textview_startDate.getText().toString().equals("Date")
                    ) {
                        // Check if time is valid format
                        boolean timeValid = false;
                        String time = texed_time.getText().toString();
                        if (time.length() == 5) {
                            if ((Integer.parseInt(time.substring(0, 1))) < 2 || (Integer.parseInt(time.substring(0, 1)) == 2 && Integer.parseInt(time.substring(1, 2)) <= 4)) {
                                if (time.substring(2, 3).equals(":") && Integer.parseInt(time.substring(3, 5)) < 60) {
                                    timeValid = true;
                                }
                            }
                        }

                        // Check if length is valid format
                        boolean lengthValid = false;
                        String length = texed_length.getText().toString();
                        if (length.length() == 5) {
                            if ((Integer.parseInt(length.substring(0, 1))) < 2 || (Integer.parseInt(length.substring(0, 1)) == 2 && Integer.parseInt(length.substring(1, 2)) <= 4)) {
                                if (length.substring(2, 3).equals(":") && Integer.parseInt(length.substring(3, 5)) < 60) {
                                    lengthValid = true;
                                }
                            }
                        }

                        if (timeValid && lengthValid) {
                            dataCorrect = true;
                        }
                    }
                } else if (inputType == 23) {
                    // Get assignment specific fields
                    TextView texed_title = findViewById(R.id.texed_title);

                    if (!texed_courseName.getText().toString().isEmpty() &&
                            !texed_time.getText().toString().isEmpty() &&
                            !texed_title.getText().toString().isEmpty() &&
                            !textview_startDate.getText().toString().equals("Date")
                    ) {
                        // Check if time is valid format
                        boolean timeValid = false;
                        String time = texed_time.getText().toString();
                        if (time.length() == 5) {
                            if ((Integer.parseInt(time.substring(0, 1))) < 2 || (Integer.parseInt(time.substring(0, 1)) == 2 && Integer.parseInt(time.substring(1, 2)) <= 4)) {
                                if (time.substring(2, 3).equals(":") && Integer.parseInt(time.substring(3, 5)) < 60) {
                                    timeValid = true;
                                }
                            }
                        }

                        if (timeValid) {
                            dataCorrect = true;
                        }
                    }
                }

                // If overall data validation is correct, perform normal button action
                if (dataCorrect) {
                    inputType += 10;
                    finish();
                } else {
                    // Else make a toast with an error
                    Toast.makeText(Pop.this, "Invalid Input", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Click - Add Item  ======================================================
        // ========================================================================
        addItemButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set up bool to check if data is correct
                boolean dataCorrect = false;

                // Get textviews
                TextView texed_courseName = findViewById(R.id.texed_courseName);
                TextView texed_time = findViewById(R.id.texed_time);
                TextView textview_startDate = findViewById(R.id.textview_startDate);

                // Check each case based on inputType
                if (inputType == 1) {

                    // Get Class specific items
                    Chip chip_sun = findViewById(R.id.chip_sun);
                    Chip chip_mon = findViewById(R.id.chip_mon);
                    Chip chip_tues = findViewById(R.id.chip_tues);
                    Chip chip_wed = findViewById(R.id.chip_wed);
                    Chip chip_thurs = findViewById(R.id.chip_thurs);
                    Chip chip_fri = findViewById(R.id.chip_fri);
                    Chip chip_sat = findViewById(R.id.chip_sat);
                    TextView texed_instructor = findViewById(R.id.texed_instructor);
                    TextView textview_endDate = findViewById(R.id.textview_endDate);
                    TextView texed_length = findViewById(R.id.texed_length);
                    TextView texed_location = findViewById(R.id.texed_location);

                    if (!texed_courseName.getText().toString().isEmpty() &&
                            !texed_time.getText().toString().isEmpty() &&
                            !textview_startDate.getText().toString().equals("Date") &&
                            !texed_instructor.getText().toString().isEmpty() &&
                            !textview_endDate.getText().toString().equals("Date") &&
                            !texed_length.getText().toString().isEmpty() &&
                            !texed_location.getText().toString().isEmpty()) {

                        // Ensure that at least one day is toggled
                        int numChecked = 0;
                        if (chip_sun.isChecked())
                            numChecked++;
                        if (chip_mon.isChecked())
                            numChecked++;
                        if (chip_tues.isChecked())
                            numChecked++;
                        if (chip_wed.isChecked())
                            numChecked++;
                        if (chip_thurs.isChecked())
                            numChecked++;
                        if (chip_fri.isChecked())
                            numChecked++;
                        if (chip_sat.isChecked())
                            numChecked++;

                        // Check if start/end date is correct
                        boolean dateValid = false;
                        int startMonth = Integer.parseInt(textview_startDate.getText().toString().substring(0, 2));
                        int startDay = Integer.parseInt(textview_startDate.getText().toString().substring(3, 5));
                        int startYear = Integer.parseInt(textview_startDate.getText().toString().substring(6));

                        int endMonth = Integer.parseInt(textview_endDate.getText().toString().substring(0, 2));
                        int endDay = Integer.parseInt(textview_endDate.getText().toString().substring(3, 5));
                        int endYear = Integer.parseInt(textview_endDate.getText().toString().substring(6));

                        if (endYear - startYear > 0) {
                            dateValid = true;
                        } else if (endYear - startYear == 0 && endMonth - startMonth > 0) {
                            dateValid = true;
                        } else if (endYear - startYear == 0 && endMonth - startMonth == 0 && endDay - startDay > 0) {
                            dateValid = true;
                        }

                        // Check if time is valid format
                        boolean timeValid = false;
                        String time = texed_time.getText().toString();
                        if (time.length() == 5) {
                            if ((Integer.parseInt(time.substring(0, 1))) < 2 || (Integer.parseInt(time.substring(0, 1)) == 2 && Integer.parseInt(time.substring(1, 2)) <= 4)) {
                                if (time.substring(2, 3).equals(":") && Integer.parseInt(time.substring(3, 5)) < 60) {
                                    timeValid = true;
                                }
                            }
                        }

                        // Check if length is valid format
                        boolean lengthValid = false;
                        String length = texed_length.getText().toString();
                        if (length.length() == 5) {
                            if ((Integer.parseInt(length.substring(0, 1))) < 2 || (Integer.parseInt(length.substring(0, 1)) == 2 && Integer.parseInt(length.substring(1, 2)) <= 4)) {
                                if (length.substring(2, 3).equals(":") && Integer.parseInt(length.substring(3, 5)) < 60) {
                                    lengthValid = true;
                                }
                            }
                        }

                        if (dateValid && timeValid && lengthValid && numChecked > 0) {
                            dataCorrect = true;
                        }
                    }
                } else if (inputType == 2) {
                    // Get exam specific items
                    TextView texed_length = findViewById(R.id.texed_length);
                    TextView texed_location = findViewById(R.id.texed_location);

                    if (!texed_courseName.getText().toString().isEmpty() &&
                            !texed_time.getText().toString().isEmpty() &&
                            !texed_location.getText().toString().isEmpty() &&
                            !texed_length.getText().toString().isEmpty() &&
                            !textview_startDate.getText().toString().equals("Date")
                    ) {
                        // Check if time is valid format
                        boolean timeValid = false;
                        String time = texed_time.getText().toString();
                        if (time.length() == 5) {
                            if ((Integer.parseInt(time.substring(0, 1))) < 2 || (Integer.parseInt(time.substring(0, 1)) == 2 && Integer.parseInt(time.substring(1, 2)) <= 4)) {
                                if (time.substring(2, 3).equals(":") && Integer.parseInt(time.substring(3, 5)) < 60) {
                                    timeValid = true;
                                }
                            }
                        }

                        // Check if length is valid format
                        boolean lengthValid = false;
                        String length = texed_length.getText().toString();
                        if (length.length() == 5) {
                            if ((Integer.parseInt(length.substring(0, 1))) < 2 || (Integer.parseInt(length.substring(0, 1)) == 2 && Integer.parseInt(length.substring(1, 2)) <= 4)) {
                                if (length.substring(2, 3).equals(":") && Integer.parseInt(length.substring(3, 5)) < 60) {
                                    lengthValid = true;
                                }
                            }
                        }

                        if (timeValid && lengthValid) {
                            dataCorrect = true;
                        }
                    }
                } else if (inputType == 3) {
                    // Get assignment specific fields
                    TextView texed_title = findViewById(R.id.texed_title);

                    if (!texed_courseName.getText().toString().isEmpty() &&
                            !texed_time.getText().toString().isEmpty() &&
                            !texed_title.getText().toString().isEmpty() &&
                            !textview_startDate.getText().toString().equals("Date")
                    ) {
                        // Check if time is valid format
                        boolean timeValid = false;
                        String time = texed_time.getText().toString();
                        if (time.length() == 5) {
                            if ((Integer.parseInt(time.substring(0, 1))) < 2 || (Integer.parseInt(time.substring(0, 1)) == 2 && Integer.parseInt(time.substring(1, 2)) <= 4)) {
                                if (time.substring(2, 3).equals(":") && Integer.parseInt(time.substring(3, 5)) < 60) {
                                    timeValid = true;
                                }
                            }
                        }

                        if (timeValid) {
                            dataCorrect = true;
                        }
                    }
                }

                // If overall data validation is correct, perform normal button action
                if (dataCorrect) {
                    inputType += 10;
                    finish();
                } else {
                    // Else make a toast with an error
                    Toast.makeText(Pop.this, "Invalid Input", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Finish  ================================================================
    // ========================================================================
    @Override
    public void finish() {
        // Prepare data intent
        Intent data = new Intent();

        // Return inputType
        data.putExtra("inputType", inputType);

        // Editing (pass sourceID so we can delete old version)
        if (inputType == 31 || inputType == 32 || inputType == 33) {
            data.putExtra("deleteID", sourceId);
        }

        // Deleting
        if (inputType == 41 || inputType == 42 || inputType == 43) {
            data.putExtra("deleteID", sourceId);
        }

        // Calendar Item: Base Class Data
        if (inputType == 11 || inputType == 12 || inputType == 13 || inputType == 31 || inputType == 32 || inputType == 33) {
            // User Inputs
            TextView texed_courseName = findViewById(R.id.texed_courseName);
            TextView texed_time = findViewById(R.id.texed_time);
            TextView textview_startDate = findViewById(R.id.textview_startDate);

            // Class Name
            String className = new String(texed_courseName.getText().toString());
            data.putExtra("className", className);
            // Time
            String time = new String(texed_time.getText().toString());
            data.putExtra("time", time);
            // Start Date
            String startDate = new String(textview_startDate.getText().toString());
            data.putExtra("startDate", startDate);
        }

        // Calendar Item: Sub Class Data
        if (inputType == 11 || inputType == 31) {
            // Adding Class
            // User Inputs
            Chip chip_sun = findViewById(R.id.chip_sun);
            Chip chip_mon = findViewById(R.id.chip_mon);
            Chip chip_tues = findViewById(R.id.chip_tues);
            Chip chip_wed = findViewById(R.id.chip_wed);
            Chip chip_thurs = findViewById(R.id.chip_thurs);
            Chip chip_fri = findViewById(R.id.chip_fri);
            Chip chip_sat = findViewById(R.id.chip_sat);
            TextView texed_instructor = findViewById(R.id.texed_instructor);
            TextView textview_endDate = findViewById(R.id.textview_endDate);
            TextView texed_length = findViewById(R.id.texed_length);
            TextView texed_location = findViewById(R.id.texed_location);

            // Days of Week
            ArrayList<Integer> daysOfWeek = new ArrayList<Integer>();
            if (chip_sun.isChecked()) {
                daysOfWeek.add(7);
            }
            if (chip_mon.isChecked()) {
                daysOfWeek.add(1);
            }
            if (chip_tues.isChecked()) {
                daysOfWeek.add(2);
            }
            if (chip_wed.isChecked()) {
                daysOfWeek.add(3);
            }
            if (chip_thurs.isChecked()) {
                daysOfWeek.add(4);
            }
            if (chip_fri.isChecked()) {
                daysOfWeek.add(5);
            }
            if (chip_sat.isChecked()) {
                daysOfWeek.add(6);
            }
            data.putExtra("daysOfWeek", daysOfWeek);
            // Instructor
            String instructor = new String(texed_instructor.getText().toString());
            data.putExtra("instructor", instructor);
            // End Date
            String endDate = new String(textview_endDate.getText().toString());
            data.putExtra("endDate", endDate);
            // Length
            String length = new String(texed_length.getText().toString());
            data.putExtra("length", length);
            // Location
            String location = new String(texed_location.getText().toString());
            data.putExtra("location", location);

        } else if (inputType == 12 || inputType == 32) {
            // Adding Exam
            // User Inputs
            TextView texed_length = findViewById(R.id.texed_length);
            TextView texed_location = findViewById(R.id.texed_location);

            // Length
            String length = new String(texed_length.getText().toString());
            data.putExtra("length", length);
            // Location
            String location = new String(texed_location.getText().toString());
            data.putExtra("location", location);

        } else if (inputType == 13  || inputType == 33) {
            // Adding Assignment
            // User Inputs
            TextView texed_title = findViewById(R.id.texed_title);

            // Assignment Title
            String title = new String(texed_title.getText().toString());
            data.putExtra("title", title);
        }

        // Activity finished ok, return the data
        setResult(RESULT_OK, data);
        super.finish();
    }

    // Update UI - Inputs  ====================================================
    // ========================================================================
    void updateInput() {

        // UI Items
        findViewById(R.id.ui_1).setVisibility(View.GONE);
        findViewById(R.id.ui_2).setVisibility(View.GONE);
        findViewById(R.id.ui_3).setVisibility(View.GONE);
        findViewById(R.id.ui_4).setVisibility(View.GONE);
        findViewById(R.id.ui_5).setVisibility(View.GONE);
        findViewById(R.id.ui_6).setVisibility(View.GONE);
        findViewById(R.id.ui_7).setVisibility(View.GONE);
        findViewById(R.id.ui_8).setVisibility(View.GONE);
        findViewById(R.id.ui_9).setVisibility(View.GONE);
        findViewById(R.id.butt_addToCalendar).setVisibility(View.GONE);
        findViewById(R.id.butt_delete).setVisibility(View.GONE);
        findViewById(R.id.butt_save).setVisibility(View.GONE);

        // Have chosen input type
        if (inputType != 0) {
            findViewById(R.id.butt_addClass).setVisibility(View.GONE);
            findViewById(R.id.butt_addExam).setVisibility(View.GONE);
            findViewById(R.id.butt_addAssignment).setVisibility(View.GONE);

            findViewById(R.id.ui_1).setVisibility(View.VISIBLE);
            findViewById(R.id.ui_2).setVisibility(View.VISIBLE);
            findViewById(R.id.ui_7).setVisibility(View.VISIBLE);

            findViewById(R.id.butt_addToCalendar).setVisibility(View.VISIBLE);

            textview_newItemType.setText("New Calendar Item:");
        }

        // Butts for Editing
        if (inputType >= 21 && inputType <= 23) {
            findViewById(R.id.butt_save).setVisibility(View.VISIBLE);
            findViewById(R.id.butt_delete).setVisibility(View.VISIBLE);
            findViewById(R.id.butt_addToCalendar).setVisibility(View.GONE);
        }

        // What inputs do we need for the selected class
        if (inputType == 1 || inputType == 21) {
            findViewById(R.id.ui_3).setVisibility(View.VISIBLE);
            findViewById(R.id.ui_4).setVisibility(View.VISIBLE);
            findViewById(R.id.ui_6).setVisibility(View.VISIBLE);
            findViewById(R.id.ui_8).setVisibility(View.VISIBLE);
            findViewById(R.id.ui_9).setVisibility(View.VISIBLE);

            if (inputType == 21) {
                textview_newItemType.setText("Edit Class:");
            } else {
                textview_newItemType.setText("New Class:");
            }
            butt_startDate.setText("Start Date");

        } else if (inputType == 2 || inputType == 22) {
            findViewById(R.id.ui_3).setVisibility(View.VISIBLE);
            findViewById(R.id.ui_6).setVisibility(View.VISIBLE);

            if (inputType == 22) {
                textview_newItemType.setText("Edit Exam:");
            } else {
                textview_newItemType.setText("New Exam:");
            }
            butt_startDate.setText("Exam Date");

        } else if (inputType == 3 || inputType == 23) {
            findViewById(R.id.ui_5).setVisibility(View.VISIBLE);

            if (inputType == 22) {
                textview_newItemType.setText("Edit Assignment:");
            } else {
                textview_newItemType.setText("New Assignment:");
            }
            butt_startDate.setText("Due Date");

        }

        // If Editing, Fill Inputs with Selected Data
        if (inputType >= 21 && inputType <= 23) {

            // Get Bundle and Extra Data
            Bundle extras = getIntent().getExtras();

            // Get textviews
            TextView texed_courseName = findViewById(R.id.texed_courseName);
            TextView texed_time = findViewById(R.id.texed_time);
            TextView texed_location = findViewById(R.id.texed_location);
            TextView texed_instructor = findViewById(R.id.texed_instructor);
            TextView texed_title = findViewById(R.id.texed_title);
            TextView texed_length = findViewById(R.id.texed_length);
            TextView textview_startDate = findViewById(R.id.textview_startDate);
            TextView textview_endDate = findViewById(R.id.textview_endDate);
            // textview_startDate

            if (inputType  == 21) {
                texed_courseName.setText(extras.getString("className"));
                texed_time.setText(extras.getString("time"));
                texed_location.setText(extras.getString("location"));
                texed_instructor.setText(extras.getString("instructorName"));
                texed_length.setText(extras.getString("length"));
                textview_startDate.setText(extras.getString("startDate"));
                textview_endDate.setText(extras.getString("endDate"));

                // Days of Week Chips
                Chip chip_sun = findViewById(R.id.chip_sun);
                Chip chip_mon = findViewById(R.id.chip_mon);
                Chip chip_tues = findViewById(R.id.chip_tues);
                Chip chip_wed = findViewById(R.id.chip_wed);
                Chip chip_thurs = findViewById(R.id.chip_thurs);
                Chip chip_fri = findViewById(R.id.chip_fri);
                Chip chip_sat = findViewById(R.id.chip_sat);

                ArrayList<Integer> days = extras.getIntegerArrayList("days");
                for (int i = 0; i < days.size(); i++) {
                    if (days.get(i) == 1) {
                    chip_mon.setChecked(true);
                    } else if (days.get(i) == 2) {
                        chip_tues.setChecked(true);
                    } else if (days.get(i) == 3) {
                        chip_wed.setChecked(true);
                    } else if (days.get(i) == 4) {
                        chip_thurs.setChecked(true);
                    } else if (days.get(i) == 5) {
                        chip_fri.setChecked(true);
                    } else if (days.get(i) == 6) {
                        chip_sat.setChecked(true);
                    } else if (days.get(i) == 7) {
                        chip_sun.setChecked(true);
                    }
                }

            } else if (inputType == 22) {
                texed_courseName.setText(extras.getString("className"));
                texed_time.setText(extras.getString("time"));
                texed_location.setText(extras.getString("location"));
                texed_length.setText(extras.getString("length"));
                textview_startDate.setText(extras.getString("startDate"));

            } else if (inputType == 23) {
                texed_courseName.setText(extras.getString("className"));
                texed_time.setText(extras.getString("time"));
                texed_title.setText(extras.getString("assignmentTitle"));
                textview_startDate.setText(extras.getString("startDate"));
            }
        }
    }
}