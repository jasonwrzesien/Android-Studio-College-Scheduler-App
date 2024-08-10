package com.example.a2340proj01.CalendarItem;

import java.time.LocalDate;
import java.time.LocalTime;

public class Exam extends CalendarItem {
    // Members
    private String location;
    private LocalTime length;

    // Constructor
    public Exam(int id, String className, LocalTime time, LocalDate startDate, String location, LocalTime length) {
        super(id, className, time, startDate);
        this.location = location;
        this.length = length;
    }
    public Exam() {
    }

    // Setters
    public void setLocation(String location) {
        this.location = location;
    }

    public void setLength(LocalTime length) {
        this.length = length;
    }

    // Getters
    public String getLocation() {
        return location;
    }

    public LocalTime getLength() {
        return length;
    }
}
