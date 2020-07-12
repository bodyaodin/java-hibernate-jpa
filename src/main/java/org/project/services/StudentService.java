package org.project.services;

import org.project.dao.DAO;
import org.project.entities.Student;
import org.project.entities.StudentGroup;
import org.project.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public class StudentService {

    private final DAO<Student> studentDAO;

    public StudentService(DAO<Student> studentDAO) {
        this.studentDAO = studentDAO;
    }

    public Student getStudent(int id) {
        return studentDAO.get(id).orElseThrow(() -> new EntityNotFoundException("There is no such entity in DB!"));
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAll();
    }

    public void saveStudent(Student newStudent) {
        studentDAO.save(newStudent);

        System.out.println(newStudent + " - was saved to DB!");
    }

    public void updateStudent(Student student, String newFirstName, String newLastName, String newEmail, String newPhone, LocalDate newBirthDate,
                              int newReportCard, int newCourse, LocalDate newEntryDate, StudentGroup newStudentGroup) {
        student.setFirstName(newFirstName);
        student.setLastName(newLastName);
        student.setEmail(newEmail);
        student.setPhone(newPhone);
        student.setBirthDate(newBirthDate);
        student.setReportCard(newReportCard);
        student.setCourse(newCourse);
        student.setEntryDate(newEntryDate);
        student.getStudentGroup().removeStudent(student);
        newStudentGroup.addStudent(student);

        studentDAO.update(student);

        System.out.println(student + " - was updated in DB!");
    }

    public void deleteStudent(Student student) {
        student.getStudentGroup().removeStudent(student);
        studentDAO.delete(student);

        System.out.println(student + " - was deleted from DB!");
    }
}
