package com.foxminded.university.models;

import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public class Group {
    private static final String LINE_BREAK = "\n";

    private int id;
    private String groupName;

    public Group() {

    }

    public Group(int id, String groupNamee) {
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
        return id == group.id &&
                groupName.equals(group.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName);
    }



    @Override
    public String toString() {
        return "Group id: " + id + LINE_BREAK +
                "Group name: " + groupName;
    }
}
