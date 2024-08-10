package com.example.a2340proj01.CalendarItem;

import java.time.LocalDate;
import java.time.LocalTime;

public class Assignment extends CalendarItem {
    // Members
    private String assignmentTitle;

    // Constructor
    public Assignment(int id, String className, LocalTime time, LocalDate startDate, String assignmentTitle) {
        super(id, className, time, startDate);
        this.assignmentTitle = assignmentTitle;
    }
    public Assignment() {
    }

    // Setters
    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    // Getters
    public String getAssignmentTitle() {
        return assignmentTitle;
    }

}
