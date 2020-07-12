package org.project.services;

import org.project.dao.DAO;
import org.project.entities.Chair;
import org.project.entities.StudentGroup;
import org.project.exceptions.EntityNotFoundException;

import java.util.List;

public class StudentGroupService {

    private final DAO<StudentGroup> studentGroupDAO;

    public StudentGroupService(DAO<StudentGroup> studentGroupDAO) {
        this.studentGroupDAO = studentGroupDAO;
    }

    public StudentGroup getStudentGroup(int id) {
        return studentGroupDAO.get(id).orElseThrow(() -> new EntityNotFoundException("There is no such entity in DB!"));
    }

    public List<StudentGroup> getAllStudentGroups() {
        return studentGroupDAO.getAll();
    }

    public void saveStudentGroup(StudentGroup newStudentGroup) {
        studentGroupDAO.save(newStudentGroup);

        System.out.println(newStudentGroup + " - was saved to DB!");
    }

    public void updateStudentGroup(StudentGroup studentGroup, String newName, Chair newChair) {
        studentGroup.setGroupName(newName);
        studentGroup.getChair().removeStudentGroup(studentGroup);
        newChair.addStudentGroup(studentGroup);

        studentGroupDAO.update(studentGroup);

        System.out.println(studentGroup + " - was updated in DB!");
    }

    public void deleteStudentGroup(StudentGroup studentGroup) {
        studentGroup.getChair().removeStudentGroup(studentGroup);
        studentGroupDAO.delete(studentGroup);

        System.out.println(studentGroup + " - was deleted from DB!");
    }
}
