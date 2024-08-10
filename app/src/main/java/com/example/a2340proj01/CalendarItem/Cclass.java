package com.example.a2340proj01.CalendarItem;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Cclass extends CalendarItem {
    // Members
    private ArrayList<Integer> daysOfWeek; // Monday = 1, Sunday = 7, etc.
    private String instructorName;
    private LocalDate endDate;
    private LocalTime length;
    private String location;

    // Constructor
    public Cclass(int id, String className, LocalTime time, LocalDate startDate, ArrayList<Integer> daysOfWeek, String instructorName, LocalDate endDate, LocalTime length, String location) {

        super(id, className, time, startDate);
        this.daysOfWeek = daysOfWeek;
        this.length = length;
        this.location = location;
        this.instructorName = instructorName;
        this.endDate = endDate;
    }
    public Cclass() {
    }

    // Setters
    public void setDaysOfWeek(ArrayList<Integer> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setLength(LocalTime length) {
        this.length = length;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // Getters
    public ArrayList<Integer> getDaysOfWeek() {
        return daysOfWeek;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalTime getLength() {
        return length;
    }

    public String getLocation() {
        return location;
    }
}
