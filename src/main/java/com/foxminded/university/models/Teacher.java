package com.foxminded.university.models;

import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class Teacher{
    private static final String LINE_BREAK = "\n";

    private int id;
    private String firstName;
    private String lastName;

    public Teacher() {

    }

    public Teacher(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
        return "Teacher id: " + id + LINE_BREAK +
                "First name: " + firstName + LINE_BREAK +
                "Last name: " + lastName;
    }
}
