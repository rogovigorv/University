package com.foxminded.university;

import java.util.Objects;

public class Lecture {
    private int id;
    private Teacher teacher;
    private String lectureName;
    private String description;
    private Timetable timeTable;

    public Lecture(int id, Teacher teacher, String lectureName, String description, Timetable timeTable) {
        this.id = id;
        this.teacher = teacher;
        this.lectureName = lectureName;
        this.description = description;
        this.timeTable = timeTable;
    }

    public int getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getLectureName() {
        return lectureName;
    }

    public String getDescription() {
        return description;
    }

    public Timetable getTimeTable() {
        return timeTable;
    }

    @Override
    public boolean equals(Object otherLecture) {
        if (this == otherLecture) {
            return true;
        }
        if (otherLecture == null || getClass() != otherLecture.getClass()) {
            return false;
        }
        Lecture lecture = (Lecture) otherLecture;
        return id == lecture.id &&
                teacher == lecture.teacher &&
                lectureName.equals(lecture.lectureName) &&
                description.equals(lecture.description) &&
                timeTable == lecture.timeTable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher, lectureName, description, timeTable);
    }

    @Override
    public String toString() {
        return "Lecture id: " + id +
                " Teacher: " + teacher.toString() +
                " Lecture name: " + lectureName +
                " Description: " + description +
                " TimeTable: " + timeTable.toString();
    }
}
