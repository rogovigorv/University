package com.foxminded.university.models;

public class TeacherDto {
    private long id;
    private String firstName;
    private String lastName;

    public TeacherDto() {
    }

    public TeacherDto(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public TeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.firstName = teacher.getFirstName();
        this.lastName = teacher.getLastName();
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

    public Teacher convertToEntity() {
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        return teacher;
    }
}
