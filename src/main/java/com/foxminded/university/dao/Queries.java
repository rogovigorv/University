package com.foxminded.university.dao;

public class Queries {
    public static final String GROUP_CREATE = "INSERT INTO groups (groupname) VALUES (?)";
    public static final String GROUP_SELECT_BY_ID = "SELECT * FROM groups WHERE id=?";
    public static final String GROUP_UPDATE_BY_ID = "UPDATE groups SET groupName=? WHERE id=?";
    public static final String GROUP_DELETE_BY_ID = "DELETE FROM groups WHERE id=?";
    public static final String GROUP_SELECT_BY_NAME = "SELECT * FROM groups WHERE groupname=?";
    public static final String GROUP_SELECT_ALL = "SELECT * FROM groups";

    public static final String LECTURE_CREATE =
            "INSERT INTO lecture (lecturename, description, teacher_id, group_id) VALUES (?, ?, ?, ?)";
    public static final String LECTURE_SELECT_BY_ID = "SELECT * FROM lecture WHERE id=?";
    public static final String LECTURE_SELECT_BY_GROUP_ID = "SELECT * FROM lecture WHERE group_id=?";
    public static final String LECTURE_UPDATE_BY_ID =
            "UPDATE lecture SET lectureName=?, description=?, teacher_id=?, group_id=? WHERE id=?";
    public static final String LECTURE_DELETE_BY_ID = "DELETE FROM lecture WHERE id=?";
    public static final String LECTURE_DELETE_BY_TEACHER_ID = "DELETE FROM lecture WHERE teacher_id=?";
    public static final String LECTURE_SELECT_ALL = "SELECT * FROM lecture";

    public static final String STUDENT_CREATE =
            "INSERT INTO student (firstname, lastname, group_id) VALUES (?, ?, ?)";
    public static final String STUDENT_SELECT_BY_ID = "SELECT * FROM student WHERE id=?";
    public static final String STUDENT_UPDATE_BY_ID =
            "UPDATE student SET firstName=?, lastName=?, group_id=? WHERE id=?";
    public static final String STUDENT_DELETE_BY_ID = "DELETE FROM student WHERE id=?";
    public static final String STUDENT_UPDATE_GROUP_BY_ID = "UPDATE student SET group_id=? WHERE id=?";
    public static final String STUDENT_SELECT_ALL = "SELECT * FROM student";

    public static final String TEACHER_CREATE = "INSERT INTO teacher (firstname, lastname) VALUES (?, ?)";
    public static final String TEACHER_SELECT_BY_ID = "SELECT * FROM teacher WHERE id=?";
    public static final String TEACHER_UPDATE_BY_ID =  "UPDATE teacher SET firstName=?, lastName=? WHERE id=?";
    public static final String TEACHER_DELETE_BY_ID = "DELETE FROM teacher WHERE id=?";
    public static final String TEACHER_SELECT_BY_LAST_NAME = "SELECT * FROM teacher WHERE lastname=?";
    public static final String TEACHER_SELECT_ALL = "SELECT * FROM teacher";
}
