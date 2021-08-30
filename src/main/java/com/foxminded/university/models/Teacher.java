package com.foxminded.university.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "teacher")
public class Teacher{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "teacher")
    private List<Lecture> lectures;

    public Teacher() {

    }

    public Teacher(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public TeacherDto convertToDto(Teacher teacher) {
        return new TeacherDto(teacher.getId(), teacher.getFirstName(), teacher.getLastName());
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

        return id == teacher.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Teacher id: " + id + "\n" +
                "First name: " + firstName + "\n" +
                "Last name: " + lastName;
    }
}
