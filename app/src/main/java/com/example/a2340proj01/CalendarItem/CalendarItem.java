package com.example.a2340proj01.CalendarItem;

import java.time.LocalDate;
import java.time.LocalTime;

public class CalendarItem {
    // Members
    private int id;
    private String className;
    private LocalTime time;
    private LocalDate startDate; // Exam = Day of Exam; Assignment = Due Date

    // Constructor
    public CalendarItem(int id, String className, LocalTime time, LocalDate startDate) {
        this.id = id;
        this.className = className;
        this.time = time;
        this.startDate = startDate;
    }
    public CalendarItem() {
    }

    // Setters
    public void setClassName(String className) {
        this.className = className;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
