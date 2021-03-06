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
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "groupname")
    private String groupName;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "group")
    private List<Lecture> lectures;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "group")
    private List<Student> students;

    public Group() {

    }

    public Group(long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public GroupDto convertToDto(Group group) {
        return new GroupDto(group.getId(), group.getGroupName());
    }

    @Override
    public boolean equals(Object otherGroup) {
        if (this == otherGroup) {
            return true;
        }
        if (otherGroup == null || getClass() != otherGroup.getClass()) {
            return false;
        }

        Group group = (Group) otherGroup;

        return id == group.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName);
    }

    @Override
    public String toString() {
        return "Group id: " + id + "\n" +
                "Group name: " + groupName;
    }
}
