package com.foxminded.university.models;

import java.util.Objects;

public class Group {
    private int id;
    private String groupName;

    public Group() {

    }

    public Group(int id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
