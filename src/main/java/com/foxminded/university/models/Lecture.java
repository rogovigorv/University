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
    private Group group;

    public Lecture() {

    }

    public Lecture(int id, Teacher teacher, String lectureName,
                   String description, Group group) {
        this.id = id;
        this.teacher = teacher;
        this.lectureName = lectureName;
        this.description = description;
        this.group = group;
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

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
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
                group == lecture.group;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher, lectureName, description, group);
    }

    @Override
    public String toString() {
        return "Lecture id: " + id + LINE_BREAK +
                "Teacher: " + LINE_BREAK +
                teacher.toString() + LINE_BREAK +
                "Lecture name: " + lectureName + LINE_BREAK +
                "Description: " + description  + LINE_BREAK +
                "Group: " + LINE_BREAK +
                group.toString();
    }
}
