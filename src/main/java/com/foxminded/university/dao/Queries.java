package com.foxminded.university.dao;

public class Queries {
    public static final String GROUP_CREATE = "INSERT INTO groups VALUES (?, ?)";
    public static final String GROUP_SELECT_BY_ID = "SELECT * FROM groups WHERE id=?";
    public static final String GROUP_UPDATE_BY_ID = "UPDATE groups SET groupName=? WHERE id=?";
    public static final String GROUP_DELETE_BY_ID = "DELETE FROM groups WHERE id=?";

    public static final String LECTURE_CREATE = "INSERT INTO lecture VALUES (?, ?, ?, ?, ?, ?)";
    public static final String LECTURE_SELECT_BY_ID = "SELECT * FROM lecture WHERE id=?";
    public static final String LECTURE_UPDATE_BY_ID =
            "UPDATE lecture SET lectureName=?, description=?, time_table_id=?, teacher_id=?, student_id=? WHERE id=?";
    public static final String LECTURE_DELETE_BY_ID = "DELETE FROM lecture WHERE id=?";

    public static final String STUDENT_CREATE = "INSERT INTO student VALUES (?, ?, ?, ?)";
    public static final String STUDENT_SELECT_BY_ID = "SELECT * FROM student WHERE id=?";
    public static final String STUDENT_UPDATE_BY_ID =
            "UPDATE student SET firstName=?, lastName=?, group_id=? WHERE id=?";
    public static final String STUDENT_DELETE_BY_ID = "DELETE FROM student WHERE id=?";

    public static final String TEACHER_CREATE = "INSERT INTO teacher VALUES (?, ?, ?)";
    public static final String TEACHER_SELECT_BY_ID = "SELECT * FROM teacher WHERE id=?";
    public static final String TEACHER_UPDATE_BY_ID =  "UPDATE teacher SET firstName=?, lastName=? WHERE id=?";
    public static final String TEACHER_DELETE_BY_ID = "DELETE FROM teacher WHERE id=?";

    public static final String TIMETABLE_CREATE = "INSERT INTO time_table VALUES (?, ?, ?)";
    public static final String TIMETABLE_SELECT_BY_ID = "SELECT * FROM time_table WHERE id=?";
    public static final String TIMETABLE_UPDATE_BY_ID = "UPDATE time_table SET start=?, duration=? WHERE id=?";
    public static final String TIMETABLE_DELETE_BY_ID = "DELETE FROM time_table WHERE id=?";
}
