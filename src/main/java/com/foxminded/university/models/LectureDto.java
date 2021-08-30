package com.foxminded.university.models;

public class LectureDto {
    private long id;
    private Teacher teacher;
    private String lectureName;
    private String description;
    private Group group;

    public LectureDto() {
    }

    public LectureDto(long id, Teacher teacher, String lectureName, String description, Group group) {
        this.id = id;
        this.teacher = teacher;
        this.lectureName = lectureName;
        this.description = description;
        this.group = group;
    }

    public LectureDto(Lecture lecture) {
        this.id = lecture.getId();
        this.teacher = lecture.getTeacher();
        this.lectureName = lecture.getLectureName();
        this.description = lecture.getDescription();
        this.group = lecture.getGroup();
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Lecture convertToEntity() {
        Lecture lecture = new Lecture();
        lecture.setId(id);
        lecture.setTeacher(teacher);
        lecture.setLectureName(lectureName);
        lecture.setDescription(description);
        lecture.setGroup(group);
        return lecture;
    }
}
