package com.example.a2340proj01;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.a2340proj01.CalendarItem.Assignment;
import com.example.a2340proj01.CalendarItem.CalendarItem;
import com.example.a2340proj01.CalendarItem.CalendarList;
import com.example.a2340proj01.CalendarItem.Cclass;
import com.example.a2340proj01.CalendarItem.Exam;
import com.example.a2340proj01.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;
import android.view.Gravity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private CalendarList calendarList;

    private LinearLayout lay_mon;
    private LinearLayout lay_tues;
    private LinearLayout lay_wed;
    private LinearLayout lay_thurs;
    private LinearLayout lay_fri;
    private LinearLayout lay_satsun;

    private TextView tv_monDate;
    private TextView tv_tuesDate;
    private TextView tv_wedDate;
    private TextView tv_thursDate;
    private TextView tv_friDate;
    private TextView tv_satDate;
    private TextView tv_sunDate;
    private TextView tv_calendarTitle;

    private LocalDate currentDay;

    private LocalDate sunday;
    private LocalDate monday;
    private LocalDate tuesday;
    private LocalDate wednesday;
    private LocalDate thursday;
    private LocalDate friday;
    private LocalDate saturday;

    private static ArrayList<String> toDoList;

    private int idCounter;

    private ImageButton filterButton;
    private boolean classVisibility;
    private boolean examVisibility;
    private boolean assignmentVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Notification Bar
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Initializing Calendar Backend
        calendarList = new CalendarList();

        if (toDoList == null) {
            toDoList = new ArrayList<>();
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Layouts of Calendar Days
        lay_mon = findViewById(R.id.lay_mon);
        lay_tues = findViewById(R.id.lay_tues);
        lay_wed = findViewById(R.id.lay_wed);
        lay_thurs = findViewById(R.id.lay_thurs);
        lay_fri = findViewById(R.id.lay_fri);
        lay_satsun = findViewById(R.id.lay_satsun);

        // Day of Week TextViewDates
        tv_monDate = findViewById(R.id.tv_monDate);
        tv_tuesDate = findViewById(R.id.tv_tuesDate);
        tv_wedDate = findViewById(R.id.tv_wedDate);
        tv_thursDate = findViewById(R.id.tv_thursDate);
        tv_friDate = findViewById(R.id.tv_friDate);
        tv_satDate = findViewById(R.id.tv_satDate);
        tv_sunDate = findViewById(R.id.tv_sunDate);
        // Year
        tv_calendarTitle = findViewById(R.id.tv_calendarTitle);

        // Buttons
        Button butt_addPop = (Button) findViewById(R.id.butt_add);
        Button butt_todoPop = (Button) findViewById(R.id.butt_todo);
        Button butt_next = (Button) findViewById(R.id.butt_next);
        Button butt_prev = (Button) findViewById(R.id.butt_prev);
        filterButton = findViewById(R.id.filterButton);

        // Date Initialization
        currentDay = LocalDate.now();

        // ID Counter Initialization
        idCounter = 0;

        // Visibility Initialization
        classVisibility = true;
        examVisibility = true;
        assignmentVisibility = true;

        // OnClick Listeners ===================================

        // To-Do Popup
        butt_todoPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, todoPop.class));
            }
        });

        // Add Calendar Item Popup Menu
        butt_addPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Setting up intent to pass data to activity to receive on finish
                Intent i = new Intent(MainActivity.this, Pop.class);
                startActivityForResult(i, 1);
            }
        });

        // Next Week Button
        butt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changing Time Frame
                currentDay = currentDay.plusDays(7);
                updateCalendarUI();
            }
        });

        // Prev Week Button
        butt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Changing Time Frame
                currentDay = currentDay.minusDays(7);
                updateCalendarUI();
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, filterPop.class);
                i.putExtra("classViz", classVisibility);
                i.putExtra("examViz", examVisibility);
                i.putExtra("assignViz", assignmentVisibility);

                startActivityForResult(i, 2);
            }
        });

        // Update UI
        updateCalendarUI();
    }

    // Input from User in PopWindow ===========================================
    // ========================================================================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If no errors                        add for protection later: data.hasExtra("returnKey1")
        if (resultCode == RESULT_OK && requestCode == 1) {

            // Get Added Type
            int addedType = 0;
            if (data.hasExtra("inputType")) {
                addedType = data.getExtras().getInt("inputType");
            }

            // Base Class Data
            String className = "";
            String time = "";
            String startDate = "";

            // Calendar Item was Added
            if (addedType == 11 || addedType == 12 || addedType == 13 || addedType == 31 || addedType == 32 || addedType == 33) {
                // Pulling Data
                if (data.hasExtra("className")) {
                    className = data.getExtras().getString("className");
                }
                if (data.hasExtra("time")) {
                    time = data.getExtras().getString("time");
                }
                if (data.hasExtra("startDate")) {
                    startDate = data.getExtras().getString("startDate");
                }
            }

            // Add to Calendar
            if (addedType == 11 || addedType == 31) {
                // Adding a Class ==============================
                // Data
                ArrayList<Integer> daysOfWeek = new ArrayList<Integer>();
                String instructor = "";
                String endDate = "";
                String length = "";
                String location = "";

                // Pulling Data
                if (data.hasExtra("daysOfWeek")) {
                    daysOfWeek = data.getExtras().getIntegerArrayList("daysOfWeek");
                }
                if (data.hasExtra("instructor")) {
                    instructor = data.getExtras().getString("instructor");
                }
                if (data.hasExtra("endDate")) {
                    endDate = data.getExtras().getString("endDate");
                }
                if (data.hasExtra("length")) {
                    length = data.getExtras().getString("length");
                }
                if (data.hasExtra("location")) {
                    location = data.getExtras().getString("location");
                }

                // Adding Class to List
                Cclass cclass = new Cclass(applyId(), className, parseTime(time), parseDate(startDate), daysOfWeek, instructor, parseDate(endDate), parseTime(length), location);
                calendarList.addClass(cclass);

            } else if (addedType == 12 || addedType == 32) {
                // Adding a Exam ===============================
                // Data
                String length = "";
                String location = "";

                // Pulling Data
                if (data.hasExtra("length")) {
                    length = data.getExtras().getString("length");
                }
                if (data.hasExtra("location")) {
                    location = data.getExtras().getString("location");
                }

                // Adding Exam to List
                Exam exam = new Exam(applyId(), className, parseTime(time), parseDate(startDate), location, parseTime(length));
                calendarList.addExam(exam);

            } else if (addedType == 13 || addedType == 33) {
                // Adding a Assignment =========================
                // Data
                String title = "";

                // Pulling Data
                if (data.hasExtra("title")) {
                    title = data.getExtras().getString("title");
                }

                // Adding Exam to List
                Assignment assignment = new Assignment(applyId(), className, parseTime(time), parseDate(startDate), title);
                calendarList.addAssignment(assignment);
            }

            // Deleting =========================
            if (addedType == 41 || addedType == 42 || addedType == 43 || addedType == 31 || addedType == 32 || addedType == 33) {

                // Get Source ID
                int deleteId = 0;
                if (data.hasExtra("deleteID")) {
                    deleteId = data.getExtras().getInt("deleteID");
                }

                // Delete Calendar Item
                if (addedType == 41 || addedType == 31) {
                    calendarList.removeClass(deleteId);
                } else if (addedType == 42  || addedType == 32) {
                    calendarList.removeExam(deleteId);
                } else {
                    calendarList.removeAssignment(deleteId);
                }
            }

            // Update Calendar
            updateCalendarUI();
        } else if (resultCode == RESULT_OK && requestCode == 2) {
            Bundle extras = data.getExtras();
            if (data.hasExtra("classViz")) {
                classVisibility = extras.getBoolean("classViz");
            }

            if (data.hasExtra("examViz")) {
                examVisibility = extras.getBoolean("examViz");
            }

            if (data.hasExtra("assignViz")) {
                assignmentVisibility = extras.getBoolean("assignViz");
            }

            updateCalendarUI();
        }
    }

    // Calendar UI Function ===================================================
    // ========================================================================
    private void updateCalendarUI() {
        // Clearing Calendar View
        clearCalendarView();
        // Getting Current Week Bounds (Sun -> Sat)
        calcCurrentWeekBounds();
        // Update Date Texts for Year and Week Days
        updateTVDates();

        // For Cycles to Switch Month Index of List
        int sunMonth = sunday.getMonthValue();
        int satMonth = saturday.getMonthValue();
        int monthListIndex;

        // Classes =============================================
        // Are 2 months in Current Week View? (Max 2 Months(cycles) in 1 week)
        boolean transitions = false;
        int cycles_class = 1;
        if (sunday.getMonthValue() != saturday.getMonthValue()) {
            cycles_class++;
            transitions = true;
        }
        monthListIndex = sunMonth;
        while (cycles_class != 0) {

            // Get Class list of current month
            ArrayList<Cclass> classList = new ArrayList<Cclass>(calendarList.getClasses(monthListIndex));

            if (!classList.isEmpty()) {
                // Class of Month
                for (int i = 0; i < classList.size(); i++) {
                    // DaysOfWeek of Class
                    ArrayList<Integer> days = new ArrayList<Integer>(classList.get(i).getDaysOfWeek());
                    if (!days.isEmpty()) {

                        // Transition Day (Day Type of First Day of Month) (if trans occurred, sat will def have it)
                        LocalDate transDay = saturday.withDayOfMonth(1);
                        int td = transDay.getDayOfWeek().getValue();

                        for (int c = 0; c < days.size(); c++) {

                            // Day Type
                            int d = days.get(c);
                            // Calculating what Date would be for Day   // Messy but converting all days of int to localDate ENUM system is better everywhere else
                            LocalDate classDay;
                            if (d == 1) {
                                classDay = monday;
                            } else if (d == 2) {
                                classDay = tuesday;
                            } else if (d == 3) {
                                classDay = wednesday;
                            } else if (d == 4) {
                                classDay = thursday;
                            } else if (d == 5) {
                                classDay = friday;
                            } else if (d == 6) {
                                classDay = saturday;
                            } else { // 7
                                classDay = sunday;
                            }
                            // Is Class in time frame? (Is day of class NOT before start date OR after end date?)
                            if (!classList.get(i).getStartDate().isAfter(classDay) && !classList.get(i).getEndDate().isBefore(classDay)) {

                                //ALSO make sure we arent duping during 2season transition
                                if (transitions && monthListIndex == sunMonth && d >= td) {
                                    // Don't go over into the next month in the same week view
                                } else if (transitions && monthListIndex == satMonth && d < td) {
                                    // Don't go back into the last month in the same week view
                                } else {
                                    // Add Class to Calendar Day Display
                                    String time = getTimeFrame(classList.get(i).getTime(), classList.get(i).getLength());
                                    String name = classList.get(i).getClassName();
                                    String location = classList.get(i).getLocation();
                                    String instructor = classList.get(i).getInstructorName();
                                    int id = classList.get(i).getId();
                                    displayClass(time, name, location, instructor, days.get(c), id);
                                }
                            }
                        }
                    }
                }
            }
            cycles_class--;
            if (cycles_class == 1) {
                monthListIndex = satMonth;
            }
        }

        // Exams ===============================================
        // Are 2 months in Current Week View? (Max 2 Months(cycles) in 1 week)
        int cycles_exams = 1;
        if (sunday.getMonthValue() != saturday.getMonthValue()) {
            cycles_exams++;
        }
        while (cycles_exams != 0) {

            // Get Exam list of current month
            ArrayList<Exam> examList = new ArrayList<Exam>(calendarList.getExams(monthListIndex));
            if (!examList.isEmpty()) {

                // Exam of Month
                for (int i = 0; i < examList.size(); i++) {

                    // Is Exam in time frame?
                    if (!examList.get(i).getStartDate().isBefore(sunday) && !examList.get(i).getStartDate().isAfter(saturday)) {
                        // Add Exam to Calendar Day Display
                        String time = getTimeFrame(examList.get(i).getTime(), examList.get(i).getLength());
                        String name = examList.get(i).getClassName();
                        String location = examList.get(i).getLocation();
                        int id = examList.get(i).getId();
                        displayExam(time, name, location, examList.get(i).getStartDate().getDayOfWeek().getValue(), id);
                    }
                }
            }
            cycles_exams--;
            if (cycles_exams == 1) {
                monthListIndex = satMonth;
            }
        }

        // Assignments =========================================
        // Are 2 months in Current Week View? (Max 2 Months(cycles) in 1 week)
        int cycles_assignments = 1;
        if (sunday.getMonthValue() != saturday.getMonthValue()) {
            cycles_assignments++;
        }
        while (cycles_assignments != 0) {

            // Get Assignment list of current month
            ArrayList<Assignment> assignmentList = new ArrayList<Assignment>(calendarList.getAssignments(monthListIndex));
            if (!assignmentList.isEmpty()) {

                // Assignment of Month
                for (int i = 0; i < assignmentList.size(); i++) {

                    // Is Assignment in time frame?
                    if (!assignmentList.get(i).getStartDate().isBefore(sunday) && !assignmentList.get(i).getStartDate().isAfter(saturday)) {
                        // Add Assignment to Calendar Day Display
                        String time = assignmentList.get(i).getTime().toString();
                        String name = assignmentList.get(i).getClassName();
                        String title = assignmentList.get(i).getAssignmentTitle();
                        int id = assignmentList.get(i).getId();
                        displayAssignment(time, name, title, assignmentList.get(i).getStartDate().getDayOfWeek().getValue(), id);
                    }
                }
            }
            cycles_assignments--;
            if (cycles_assignments == 1) {
                monthListIndex = satMonth;
            }
        }
    }

    // Display  Functions =====================================================
    // ========================================================================
    private void displayClass(String time, String name, String location, String instructor, int day, int id) {
        if (classVisibility) {
            // Class Layout
            int rightMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);
            int topMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);
            int bottomMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);

            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            layoutparams.rightMargin = rightMargin;
            layoutparams.leftMargin = rightMargin;
            layoutparams.topMargin = topMargin;
            layoutparams.bottomMargin = bottomMargin;

            LinearLayout classLay = new LinearLayout(this);
            classLay.setOrientation(LinearLayout.VERTICAL);
            classLay.setLayoutParams(layoutparams);
            classLay.setBackgroundColor(Color.parseColor("#B3A369"));

            // ID
            int displayId = 1000 + id;
            classLay.setId(displayId);

            // Text View: Time
            TextView tv_time = new TextView(this);
            tv_time.setText(time);
            tv_time.setTextColor(Color.parseColor("#ffffff"));
            tv_time.setGravity(Gravity.CENTER_HORIZONTAL);
            // Text View: Name
            TextView tv_className = new TextView(this);
            tv_className.setText(name);
            tv_className.setTextColor(Color.parseColor("#ffffff"));
            tv_className.setGravity(Gravity.CENTER_HORIZONTAL);
            // Text View: Location
            TextView tv_location = new TextView(this);
            tv_location.setText(location);
            tv_location.setTextColor(Color.parseColor("#ffffff"));
            tv_location.setGravity(Gravity.CENTER_HORIZONTAL);
            // Text View: Instructor
            TextView tv_instructor = new TextView(this);
            tv_instructor.setText(instructor);
            tv_instructor.setTextColor(Color.parseColor("#ffffff"));
            tv_instructor.setGravity(Gravity.CENTER_HORIZONTAL);

            // Add To Block
            classLay.addView(tv_time);
            classLay.addView(tv_className);
            classLay.addView(tv_location);
            classLay.addView(tv_instructor);

            // Add Block to Calendar Day
            addBlock(classLay, day);
        }
    }

    private void displayExam(String time, String name, String location, int day, int id) {
        if (examVisibility) {
            // Class Layout
            int rightMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);
            int topMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);
            int bottomMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);

            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            layoutparams.rightMargin = rightMargin;
            layoutparams.leftMargin = rightMargin;
            layoutparams.topMargin = topMargin;
            layoutparams.bottomMargin = bottomMargin;

            LinearLayout examLay = new LinearLayout(this);
            examLay.setOrientation(LinearLayout.VERTICAL);
            examLay.setLayoutParams(layoutparams);
            examLay.setBackgroundColor(Color.parseColor("#54585A"));

            // ID
            int displayId = 2000 + id;
            examLay.setId(displayId);

            // Text View: Time
            TextView tv_time = new TextView(this);
            tv_time.setText(time);
            tv_time.setTextColor(Color.parseColor("#ffffff"));
            tv_time.setGravity(Gravity.CENTER_HORIZONTAL);
            // Text View: Name
            TextView tv_className = new TextView(this);
            tv_className.setText(name);
            tv_className.setTextColor(Color.parseColor("#ffffff"));
            tv_className.setGravity(Gravity.CENTER_HORIZONTAL);
            // Text View: Location
            TextView tv_location = new TextView(this);
            tv_location.setText(location);
            tv_location.setTextColor(Color.parseColor("#ffffff"));
            tv_location.setGravity(Gravity.CENTER_HORIZONTAL);

            // Add To Block
            examLay.addView(tv_time);
            examLay.addView(tv_className);
            examLay.addView(tv_location);

            // Add Block to Calendar Day
            addBlock(examLay, day);
        }
    }

    private void displayAssignment(String time, String name, String title, int day, int id) {
        if (assignmentVisibility) {
            // Class Layout
            int rightMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);
            int topMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);
            int bottomMargin = getResources().getDimensionPixelSize(R.dimen.item_margin);

            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

            layoutparams.rightMargin = rightMargin;
            layoutparams.leftMargin = rightMargin;
            layoutparams.topMargin = topMargin;
            layoutparams.bottomMargin = bottomMargin;

            LinearLayout assignmentLay = new LinearLayout(this);
            assignmentLay.setOrientation(LinearLayout.VERTICAL);
            assignmentLay.setLayoutParams(layoutparams);
            assignmentLay.setBackgroundColor(Color.parseColor("#EAAA00"));

            // ID
            int displayId = 3000 + id;
            assignmentLay.setId(displayId);

            // Text View: Time
            TextView tv_time = new TextView(this);
            tv_time.setText(time);
            tv_time.setTextColor(Color.parseColor("#ffffff"));
            tv_time.setGravity(Gravity.CENTER_HORIZONTAL);
            // Text View: Name
            TextView tv_className = new TextView(this);
            tv_className.setText(name);
            tv_className.setTextColor(Color.parseColor("#ffffff"));
            tv_className.setGravity(Gravity.CENTER_HORIZONTAL);
            // Text View: Title
            TextView tv_title = new TextView(this);
            tv_title.setText(title);
            tv_title.setTextColor(Color.parseColor("#ffffff"));
            tv_title.setGravity(Gravity.CENTER_HORIZONTAL);

            // Add To Block
            assignmentLay.addView(tv_time);
            assignmentLay.addView(tv_className);
            assignmentLay.addView(tv_title);

            // Add Block to Calendar Day
            addBlock(assignmentLay, day);
        }
    }

    // Getters  ===============================================================
    // ========================================================================
    public static ArrayList<String> getToDoList() {
        return toDoList;
    }

    // Helper Functions =======================================================
    // ========================================================================

    // Gets XX:XX - XX:XX using StartingTime and Duration(Length)
    private String getTimeFrame(LocalTime startTime, LocalTime duration) {

        int h = startTime.getHour() + duration.getHour();
        int m = startTime.getMinute() + duration.getMinute();

        // Assumes only double digit input for minutes
        if (m >= 60) {
            h++;
            int oldM = m;
            m = oldM - 60;
        }

        LocalTime endTime = LocalTime.of(h, m);

        String frame = startTime.toString() + " - " + endTime.toString();
        return frame;
    }

    // Clears Calendar View
    private void clearCalendarView(){
        lay_satsun.removeAllViews();
        lay_mon.removeAllViews();
        lay_tues.removeAllViews();
        lay_wed.removeAllViews();
        lay_thurs.removeAllViews();
        lay_fri.removeAllViews();
    }

    // Calculates Current Week Bounds (Sun -> Sat)
    private void calcCurrentWeekBounds() {
        // Go backward to get Sun
        sunday = currentDay;
        while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
            sunday = sunday.minusDays(1);
        }
        // Go forward to get Saturday
        saturday = currentDay;
        while (saturday.getDayOfWeek() != DayOfWeek.SATURDAY) {
            saturday = saturday.plusDays(1);
        }

        //Get All Days of Week
        monday = sunday.plusDays(1);
        tuesday = sunday.plusDays(2);
        wednesday = sunday.plusDays(3);
        thursday = sunday.plusDays(4);
        friday = sunday.plusDays(5);
    }

    // Update Date Texts for Year and Week Days
    private void updateTVDates() {
        //Set TVDates with formatting
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("MM/dd");
        tv_sunDate.setText(sunday.format(pattern));
        tv_monDate.setText(monday.format(pattern));
        tv_tuesDate.setText(tuesday.format(pattern));
        tv_wedDate.setText(wednesday.format(pattern));
        tv_thursDate.setText(thursday.format(pattern));
        tv_friDate.setText(friday.format(pattern));
        tv_satDate.setText(saturday.format(pattern));

        // Year
        DateTimeFormatter pattern2 = DateTimeFormatter.ofPattern("yyyy");
        tv_calendarTitle.setText(sunday.format(pattern2));
    }

    private void addBlock(LinearLayout layout, int day){
        // Add Block to Calendar Day (these based off of localDate ENUM)
        if (day == 6 || day == 7) {
            lay_satsun.addView(layout);
        } else if (day == 1) {
            lay_mon.addView(layout);
        } else if (day == 2) {
            lay_tues.addView(layout);
        } else if (day == 3) {
            lay_wed.addView(layout);
        } else if (day == 4) {
            lay_thurs.addView(layout);
        } else if (day == 5) {
            lay_fri.addView(layout);
        }

       layout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Process Id to get Type
                int type = layout.getId();
                while (type > 9) {
                    type/=10;
                }

                // Setting up intent to pass data to activity to receive on finish
                Intent i = new Intent(MainActivity.this, Pop.class);

                // Date and Time Formatters
                DateTimeFormatter dtf_time = DateTimeFormatter.ofPattern("HH:mm");
                DateTimeFormatter dtf_date = DateTimeFormatter.ofPattern("MM-dd-yyyy");

                // Process Id to get actual Calendar Item Id
                if (type == 1) {

                    // Set Up Data for Intent
                    int id = (Integer)layout.getId() - 1000;
                    i.putExtra("state", 21);
                    i.putExtra("sourceID", id);

                    // Data for Selected Class
                    Cclass classItem = calendarList.getClass(id);
                    i.putExtra("className", classItem.getClassName());
                    i.putExtra("time", classItem.getTime().format(dtf_time));
                    i.putExtra("startDate", classItem.getStartDate().format(dtf_date));
                    i.putExtra("daysOfWeek", classItem.getDaysOfWeek());
                    i.putExtra("length", classItem.getLength().format(dtf_time));
                    i.putExtra("location", classItem.getLocation());
                    i.putExtra("instructorName", classItem.getInstructorName());
                    i.putExtra("endDate", classItem.getEndDate().format(dtf_date));
                    i.putExtra("days", classItem.getDaysOfWeek());

                } else if (type == 2) {

                    // Set Up Data for Intent
                    int id = (Integer)layout.getId() - 2000;
                    i.putExtra("state", 22);
                    i.putExtra("sourceID", id);

                    // Data for Selected Exam
                    Exam examItem = calendarList.getExam(id);
                    i.putExtra("className", examItem.getClassName());
                    i.putExtra("time", examItem.getTime().format(dtf_time));
                    i.putExtra("startDate", examItem.getStartDate().format(dtf_date));
                    i.putExtra("location", examItem.getLocation());
                    i.putExtra("length", examItem.getLength().format(dtf_time));

                } else if (type == 3) {

                    // Set Up Data for Intent
                    int id = (Integer)layout.getId() - 3000;
                    i.putExtra("state", 23);
                    i.putExtra("sourceID", id);

                    // Data for Selected Assignment
                    Assignment assignmentItem = calendarList.getAssignment(id);
                    i.putExtra("className", assignmentItem.getClassName());
                    i.putExtra("time", assignmentItem.getTime().format(dtf_time));
                    i.putExtra("startDate", assignmentItem.getStartDate().format(dtf_date));
                    i.putExtra("assignmentTitle", assignmentItem.getAssignmentTitle());

                } else {
                    //shit pants
                }

                // Run Popup
                startActivityForResult(i, 1);
            }
        });
    }

    // String -> LocalTime (Formatted)
    private LocalTime parseTime (String time) {
       return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }

    // String -> LocalDate (Formatted)
    private LocalDate parseDate (String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
    }

    // Apply ID for CalendarItem (ONLY USE THIS, DO NOT SET ID OTHER WAY!!!)
    private int applyId() {
        idCounter++;
        return idCounter;
    }
}