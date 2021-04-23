package com.foxminded.university.service;

import com.foxminded.university.dao.GroupDao;
import com.foxminded.university.dao.LectureDao;
import com.foxminded.university.dao.StudentDao;
import com.foxminded.university.dao.TeacherDao;
import com.foxminded.university.models.Group;
import com.foxminded.university.models.Lecture;
import com.foxminded.university.models.Student;
import com.foxminded.university.models.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {
    private final GroupDao groupDao;
    private final TeacherDao teacherDao;
    private final StudentDao studentDao;
    private final LectureDao lectureDao;

    @Autowired
    public UniversityService(GroupDao groupDao, TeacherDao teacherDao,
                             StudentDao studentDao, LectureDao lectureDao) {
        this.groupDao = groupDao;
        this.teacherDao = teacherDao;
        this.studentDao = studentDao;
        this.lectureDao = lectureDao;
    }

    public void createTwoGroups() {
        Group madOrangeGroup = new Group(1, "Mad Orange");
        Group rimRockDriveGroup = new Group(2, "Rim Rock Drive");

        groupDao.create(madOrangeGroup);
        groupDao.create(rimRockDriveGroup);
    }

    public void createTwoTeachers() {
        Teacher yakovGoldshtein = new Teacher(1, "Yakov", "Goldshtein");
        Teacher zinovijKuroedov = new Teacher(2, "Zinovij", "Kuroedov");

        teacherDao.create(yakovGoldshtein);
        teacherDao.create(zinovijKuroedov);
    }

    public void createTwoStudents() {
        Group groupId1 = groupDao.getById(1);
        Group groupId2 = groupDao.getById(2);

        Student maksimMumrikov = new Student(1, "Maksim", "Mumrikov", groupId1);
        Student denisDarmoedov = new Student(2, "Denis", "Darmoedov", groupId2);

        studentDao.create(maksimMumrikov);
        studentDao.create(denisDarmoedov);
    }

    public void createTwoLectures() {
        Teacher teacherId1 = teacherDao.getById(1);
        Group groupId1 = groupDao.getById(1);

        Teacher teacherId2 = teacherDao.getById(2);
        Group groupId2 = groupDao.getById(2);

        Lecture math = new Lecture(1, teacherId1, "Math", "Simple math", groupId1);
        Lecture biology = new Lecture(2, teacherId2, "Biology", "Simple biology", groupId2);

        lectureDao.create(math, 1);
        lectureDao.create(biology, 2);
    }

    public void changeStudentGroup() {
        int studentId = 1;
        int newGroupId = 2;

        studentDao.updateGroup(studentId, newGroupId);
    }

    public void changeStudentFirstName() {
        int id = 1;
        String newName = "Mike";

        studentDao.updateName(id, newName);
    }

    public void changeLectureTeacher() {
        int lectureId = 2;
        int teacherId = 1;

        lectureDao.updateTeacher(teacherId, lectureId);
    }
}
