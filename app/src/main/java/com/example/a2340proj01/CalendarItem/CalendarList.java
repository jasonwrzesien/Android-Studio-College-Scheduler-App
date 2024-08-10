package com.example.a2340proj01.CalendarItem;

import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Map;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;

public class CalendarList {

    // Members
    private Map<Integer, ArrayList<Cclass>> classes;
    private Map<Integer, ArrayList<Exam>> exams;
    private Map<Integer, ArrayList<Assignment>> assignments;

    // Constructor
    public CalendarList() {
        classes = new HashMap<>();
        exams = new HashMap<>();
        assignments = new HashMap<>();
    }

    // Add to List Functions, All keys based on StartDate Month ===============
    // ========================================================================
    public void addClass(Cclass cclass) {
        // Key Based on Month
        int mKey = cclass.getStartDate().getMonthValue();

        // How Many Months in between
        int range = cclass.getEndDate().getMonthValue() - mKey;
        for(int i = mKey; i <= mKey + range; i++) {
            // Adding to List
            if (!classes.containsKey(i)) {
                classes.put(i, new ArrayList<Cclass>());
            }
            classes.get(i).add(cclass);
        }
    }

    public void addExam(Exam exam) {
        // Key Based on Month
        int mKey = exam.getStartDate().getMonthValue();
        // Adding to List
        if (!exams.containsKey(mKey)) {
            exams.put(mKey, new ArrayList<Exam>());
        }
        exams.get(mKey).add(exam);
    }

    public void addAssignment(Assignment assignment) {
        // Key Based on Month
        int mKey = assignment.getStartDate().getMonthValue();
        // Adding to List
        if (!assignments.containsKey(mKey)) {
            assignments.put(mKey, new ArrayList<Assignment>());
        }
        assignments.get(mKey).add(assignment);
    }

    // Remove from List Functions (these are quick unopt implementations) =====
    // ========================================================================
    public void removeClass(int id) {

        // Iterate through map of Classes
        Iterator it = classes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            // Month
            int mKey = (Integer)pair.getKey();
            for(int i = 0; i < classes.get(mKey).size(); i++) {

                // ID Checking
                if (classes.get(mKey).get(i).getId() == id) {
                    classes.get(mKey).remove(i);

                    if (classes.get(mKey).isEmpty()) {
                        it.remove();
                    }

                    break;
                }
            }
        }
    }

    public void removeExam(int id) {

        // Iterate through map of Exams
        Iterator it = exams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            // Month
            int mKey = (Integer)pair.getKey();
            for(int i = 0; i < exams.get(mKey).size(); i++) {

                // ID Checking
                if (exams.get(mKey).get(i).getId() == id) {
                    exams.get(mKey).remove(i);

                    if (exams.get(mKey).isEmpty()) {
                        it.remove();
                    }

                    break;
                }
            }
        }
    }

    public void removeAssignment(int id) {

        // Iterate through map of Assignments
        Iterator it = assignments.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            // Month
            int mKey = (Integer)pair.getKey();
            for(int i = 0; i < assignments.get(mKey).size(); i++) {

                // ID Checking
                if (assignments.get(mKey).get(i).getId() == id) {
                    assignments.get(mKey).remove(i);

                    if (assignments.get(mKey).isEmpty()) {
                        it.remove();
                    }

                    break;
                }
            }
        }
    }

    // Get All of Type for Month ==============================================
    // ========================================================================
    public ArrayList<Cclass> getClasses(int m) {
        ArrayList<Cclass> classList = new ArrayList<Cclass>();

        if (classes.containsKey(m)) {
            for (int i = 0; i < classes.get(m).size(); i++) {
                classList.add(classes.get(m).get(i));
            }
        }
        return classList;
    }

    public ArrayList<Exam> getExams(int m) {
        ArrayList<Exam> examList = new ArrayList<Exam>();

        if (exams.containsKey(m)) {
            for (int i = 0; i < exams.get(m).size(); i++) {
                examList.add(exams.get(m).get(i));
            }
        }
        return examList;
    }

    public ArrayList<Assignment> getAssignments(int m) {
        ArrayList<Assignment> assignmentList = new ArrayList<Assignment>();

        if (assignments.containsKey(m)) {
            for (int i = 0; i < assignments.get(m).size(); i++) {
                assignmentList.add(assignments.get(m).get(i));
            }
        }
        return assignmentList;
    }

    // Get By Id ==============================================================
    // ========================================================================
    public Cclass getClass(int id) {

        // Iterate through map of Exams
        Iterator it = classes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            // Month
            int mKey = (Integer)pair.getKey();
            for(int i = 0; i < classes.get(mKey).size(); i++) {

                // ID Checking
                if (classes.get(mKey).get(i).getId() == id) {
                    return classes.get(mKey).get(i);
                }
            }
        }
        return new Cclass();
    }

    public Exam getExam(int id) {

        // Iterate through map of Exams
        Iterator it = exams.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            // Month
            int mKey = (Integer)pair.getKey();
            for(int i = 0; i < exams.get(mKey).size(); i++) {

                // ID Checking
                if (exams.get(mKey).get(i).getId() == id) {
                    return exams.get(mKey).get(i);
                }
            }
        }
        return new Exam();
    }

    public Assignment getAssignment(int id) {

        // Iterate through map of Assignments
        Iterator it = assignments.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            // Month
            int mKey = (Integer)pair.getKey();
            for(int i = 0; i < assignments.get(mKey).size(); i++) {

                // ID Checking
                if (assignments.get(mKey).get(i).getId() == id) {
                    return assignments.get(mKey).get(i);
                }
            }
        }
        return new Assignment();
    }
}