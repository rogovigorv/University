package com.foxminded.university.models;

import java.util.Objects;

public class Lecture {
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
                teacher.equals(lecture.getTeacher()) &&
                lectureName.equals(lecture.lectureName) &&
                description.equals(lecture.description) &&
                group.equals(lecture.getGroup());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, teacher, lectureName, description, group);
    }

    @Override
    public String toString() {
        return "Lecture id: " + id + "\n" +
                "Teacher: " + "\n" +
                teacher.toString() + "\n" +
                "Lecture name: " + lectureName + "\n" +
                "Description: " + description  + "\n" +
                "Group: " + "\n" +
                group.toString();
    }
}
