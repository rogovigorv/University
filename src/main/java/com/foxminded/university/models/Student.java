package com.foxminded.university.models;

import java.util.List;
import java.util.Objects;

public class Student {
    private int id;
    private String firstName;
    private String lastName;
    private Group group;
    private List<Lecture> timeTable;

    public Student() {

    }

    public Student(int id, String firstName, String lastName, Group group, List<Lecture> timeTable) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
        this.timeTable = timeTable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Lecture> getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(List<Lecture> timeTable) {
        this.timeTable = timeTable;
    }

    @Override
    public boolean equals(Object otherStudent) {
        if (this == otherStudent) {
            return true;
        }
        if (otherStudent == null || getClass() != otherStudent.getClass()) {
            return false;
        }
        Student student = (Student) otherStudent;
        return id == student.id &&
                firstName.equals(student.firstName) &&
                lastName.equals(student.lastName) &&
                group == student.group &&
                timeTable == student.timeTable;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, group, timeTable);
    }

    @Override
    public String toString() {
        return "Student id: " + id +
                " First name: " + firstName +
                " Last name: " + lastName +
                " Group: " + group.toString() +
                " TimeTable: " + timeTable.toString();
    }
}
