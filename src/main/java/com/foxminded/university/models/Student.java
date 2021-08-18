package com.foxminded.university.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Group.class)
    @JoinColumn(name = "student_group_id")
    private Group group;

    public Student() {

    }

    public Student(int id, String firstName, String lastName, Group group) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.group = group;
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
                lastName.equals(student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, group);
    }

    @Override
    public String toString() {
        return "Student id: " + id + "\n" +
                "First name: " + firstName + "\n" +
                "Last name: " + lastName + "\n" +
                "Group: " + "\n" +
                group.toString();
    }
}
