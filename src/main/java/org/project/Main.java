package org.project;

import org.project.dao.*;
import org.project.entities.*;
import org.project.services.*;
import org.project.managers.EntityManagerFactoryCreator;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = EntityManagerFactoryCreator.getInstance();

        FacultyDAOImpl facultyDAO = new FacultyDAOImpl(entityManagerFactory);
        ChairDAOImpl chairDAO = new ChairDAOImpl(entityManagerFactory);
        StudentGroupDAOImpl studentGroupDAO = new StudentGroupDAOImpl(entityManagerFactory);
        StudentDAOImpl studentDAO = new StudentDAOImpl(entityManagerFactory);
        SubjectDAOImpl subjectDAO = new SubjectDAOImpl(entityManagerFactory);
        SessionDAOImpl sessionDAO = new SessionDAOImpl(entityManagerFactory);

        FacultyService facultyService = new FacultyService(facultyDAO);
        ChairService chairService = new ChairService(chairDAO);
        StudentGroupService studentGroupService = new StudentGroupService(studentGroupDAO);
        StudentService studentService = new StudentService(studentDAO);
        SubjectService subjectService = new SubjectService(subjectDAO);
        SessionService sessionService = new SessionService(sessionDAO);

        //--------------------------------------------------------------------

        facultyOperationsStart(facultyService);
//        chairOperationsStart(chairService, facultyService);
//        studentGroupOperationsStart(studentGroupService, chairService);
//        studentOperationsStart(studentService, studentGroupService);
//        subjectOperationsStart(subjectService);
//        sessionOperationsStart(sessionService, studentService, subjectService);

        //--------------------------------------------------------------------

        entityManagerFactory.close();
    }

    private static void facultyOperationsStart(FacultyService facultyService) {
        facultyService.getAllFaculties().forEach(System.out::println);

        Faculty newFaculty = new Faculty();
        newFaculty.setId(3);
        newFaculty.setFacultyName("New Faculty!");

        facultyService.saveFaculty(newFaculty);
        System.out.println(facultyService.getFaculty(newFaculty.getId()));
        facultyService.getAllFaculties().forEach(System.out::println);

        facultyService.updateFaculty(newFaculty, "New Faculty Updated!!!");
        facultyService.getAllFaculties().forEach(System.out::println);

        facultyService.deleteFaculty(newFaculty);
        facultyService.getAllFaculties().forEach(System.out::println);
    }

    private static void chairOperationsStart(ChairService chairService, FacultyService facultyService) {
        chairService.getAllChairs().forEach(System.out::println);

        Chair newChair = new Chair();
        newChair.setId(33);
        newChair.setChairName("New Chair!");

        Faculty faculty = facultyService.getFaculty(1);
        faculty.addChair(newChair);

        chairService.saveChair(newChair);
        System.out.println(chairService.getChair(newChair.getId()));
        chairService.getAllChairs().forEach(System.out::println);

        Faculty faculty2 = facultyService.getFaculty(2);
        chairService.updateChair(newChair, "New Chair Updated!!!", faculty2);
        chairService.getAllChairs().forEach(System.out::println);

        chairService.deleteChair(newChair);
        chairService.getAllChairs().forEach(System.out::println);
    }

    private static void studentGroupOperationsStart(StudentGroupService studentGroupService, ChairService chairService) {
        studentGroupService.getAllStudentGroups().forEach(System.out::println);

        StudentGroup newStudentGroup = new StudentGroup();
        newStudentGroup.setId(333);
        newStudentGroup.setGroupName("New Student Group!");

        Chair chair = chairService.getChair(11);
        chair.addStudentGroup(newStudentGroup);

        studentGroupService.saveStudentGroup(newStudentGroup);
        System.out.println(studentGroupService.getStudentGroup(newStudentGroup.getId()));
        studentGroupService.getAllStudentGroups().forEach(System.out::println);

        Chair chair2 = chairService.getChair(12);
        studentGroupService.updateStudentGroup(newStudentGroup, "New Student Group Updated!!!", chair2);
        studentGroupService.getAllStudentGroups().forEach(System.out::println);

        studentGroupService.deleteStudentGroup(newStudentGroup);
        studentGroupService.getAllStudentGroups().forEach(System.out::println);
    }

    private static void studentOperationsStart(StudentService studentService, StudentGroupService studentGroupService) {
        studentService.getAllStudents().forEach(System.out::println);

        Student newStudent = new Student();
        newStudent.setId(33);
        newStudent.setFirstName("New Name");
        newStudent.setLastName("New Last Name");
        newStudent.setEmail("new@gmail.com");
        newStudent.setPhone("+380958565485");
        newStudent.setBirthDate(LocalDate.of(2020, 11, 30));
        newStudent.setReportCard(55555);
        newStudent.setCourse(5);
        newStudent.setEntryDate(LocalDate.now());

        StudentGroup studentGroup = studentGroupService.getStudentGroup(111);
        studentGroup.addStudent(newStudent);

        studentService.saveStudent(newStudent);
        System.out.println(studentService.getStudent(newStudent.getId()));
        studentService.getAllStudents().forEach(System.out::println);

        StudentGroup studentGroup2 = studentGroupService.getStudentGroup(112);
        studentService.updateStudent(newStudent, "Updated Name!!!", "Updated Last Name",
                "newEmail@gmail.com", "911", LocalDate.now(), 123, 3, LocalDate.now(), studentGroup2);
        studentService.getAllStudents().forEach(System.out::println);

        studentService.deleteStudent(newStudent);
        studentService.getAllStudents().forEach(System.out::println);
    }

    private static void subjectOperationsStart(SubjectService subjectService) {
        subjectService.getAllSubjects().forEach(System.out::println);

        Subject newSubject = new Subject();
        newSubject.setId(5);
        newSubject.setSubjectName("New Subject!");

        subjectService.saveSubject(newSubject);
        System.out.println(subjectService.getSubject(newSubject.getId()));
        subjectService.getAllSubjects().forEach(System.out::println);

        subjectService.updateSubject(newSubject, "New Subject Updated!!!");
        subjectService.getAllSubjects().forEach(System.out::println);

        subjectService.deleteSubject(newSubject);
        subjectService.getAllSubjects().forEach(System.out::println);
    }

    private static void sessionOperationsStart(SessionService sessionService, StudentService studentService, SubjectService subjectService) {
        sessionService.getAllSessions().forEach(System.out::println);

        Session newSession = new Session();
        newSession.setId(99);
        newSession.setExamDate(LocalDate.now());
        newSession.setSemester(2);
        newSession.setGrade(5);

        Student student = studentService.getStudent(6);
        student.addSession(newSession);

        Subject subject = subjectService.getSubject(1);
        newSession.setSubject(subject);

        sessionService.saveSession(newSession);
        System.out.println(sessionService.getSession(newSession.getId()));
        sessionService.getAllSessions().forEach(System.out::println);

        Student student2 = studentService.getStudent(12);
        Subject subject2 = subjectService.getSubject(2);
        sessionService.updateSession(newSession, LocalDate.now(), 1, student2, subject2, 4);
        sessionService.getAllSessions().forEach(System.out::println);

        sessionService.deleteSession(newSession);
        sessionService.getAllSessions().forEach(System.out::println);
    }
}
