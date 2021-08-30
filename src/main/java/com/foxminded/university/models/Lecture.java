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
@Table(name = "lecture")
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Teacher.class)
    @JoinColumn(name = "lecture_teacher_id")
    private Teacher teacher;

    @Column(name = "lecturename")
    private String lectureName;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, targetEntity = Group.class)
    @JoinColumn(name = "lecture_group_id")
    private Group group;

    public Lecture() {

    }

    public Lecture(long id, Teacher teacher, String lectureName,
                   String description, Group group) {
        this.id = id;
        this.teacher = teacher;
        this.lectureName = lectureName;
        this.description = description;
        this.group = group;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LectureDto convertToDto(Lecture lecture) {
        return new LectureDto(lecture.getId(), lecture.getTeacher(),
                lecture.getLectureName(), lecture.getDescription(), lecture.getGroup());
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
