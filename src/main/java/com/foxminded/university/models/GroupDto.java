package com.foxminded.university.models;

public class GroupDto {
    private long id;
    private String groupName;

    public GroupDto() {
    }

    public GroupDto(long id, String groupName) {
        this.id = id;
        this.groupName = groupName;
    }

    public GroupDto(Group group) {
        this.id = group.getId();
        this.groupName = group.getGroupName();
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

    public Group convertToEntity() {
        Group group = new Group();
        group.setId(id);
        group.setGroupName(groupName);
        return group;
    }
}
