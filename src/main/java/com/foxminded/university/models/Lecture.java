package com.foxminded.university.models;

import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class Lecture {
    private static final String LINE_BREAK = "\n";

    private int id;
    private Teacher teacher;
    private String lectureName;
    private String description;

    public Lecture() {

    }

    public Lecture(int id, Teacher teacher, String lectureName, String description) {
        this.id = id;
        this.teacher = teacher;
        this.lectureName = lectureName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                description.equals(lecture.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher, lectureName, description);
    }

    @Override
    public String toString() {
        return "Lecture id: " + id + LINE_BREAK +
                "Teacher: " + teacher.toString() + LINE_BREAK +
                "Lecture name: " + lectureName + LINE_BREAK +
                "Description: " + description;
    }
}
