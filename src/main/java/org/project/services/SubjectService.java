package org.project.services;

import org.project.dao.DAO;
import org.project.entities.Subject;
import org.project.exceptions.EntityNotFoundException;

import java.util.List;

public class SubjectService {

    private final DAO<Subject> subjectDAO;

    public SubjectService(DAO<Subject> subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    public Subject getSubject(int id) {
        return subjectDAO.get(id).orElseThrow(() -> new EntityNotFoundException("There is no such entity in DB!"));
    }

    public List<Subject> getAllSubjects() {
        return subjectDAO.getAll();
    }

    public void saveSubject(Subject newSubject) {
        subjectDAO.save(newSubject);

        System.out.println(newSubject + " - was saved to DB!");
    }

    public void updateSubject(Subject subject, String newName) {
        subject.setSubjectName(newName);
        subjectDAO.update(subject);

        System.out.println(subject + " - was updated in DB!");
    }

    public void deleteSubject(Subject subject) {
        subjectDAO.delete(subject);

        System.out.println(subject + " - was deleted from DB!");
    }
}
