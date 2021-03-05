package com.foxminded.university;

import java.util.Objects;

public class Teacher{
    private final int id;
    private final String firstName;
    private final String lastName;

    public Teacher(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object otherTeacher) {
        if (this == otherTeacher) {
            return true;
        }
        if (otherTeacher == null || getClass() != otherTeacher.getClass()) {
            return false;
        }
        Teacher teacher = (Teacher) otherTeacher;
        return id == teacher.id &&
                firstName.equals(teacher.firstName) &&
                lastName.equals(teacher.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Teacher id: " + id +
                " First name: " + firstName +
                " Last name: " + lastName;
    }
}
