package org.project.services;

import org.project.dao.DAO;
import org.project.entities.Faculty;
import org.project.exceptions.EntityNotFoundException;

import java.util.List;

public class FacultyService {

    private final DAO<Faculty> facultyDAO;

    public FacultyService(DAO<Faculty> facultyDAO) {
        this.facultyDAO = facultyDAO;
    }

    public Faculty getFaculty(int id) {
        return facultyDAO.get(id).orElseThrow(() -> new EntityNotFoundException("There is no such entity in DB!"));
    }

    public List<Faculty> getAllFaculties() {
        return facultyDAO.getAll();
    }

    public void saveFaculty(Faculty newFaculty) {
        facultyDAO.save(newFaculty);

        System.out.println(newFaculty + " - was saved to DB!");
    }

    public void updateFaculty(Faculty faculty, String newName) {
        faculty.setFacultyName(newName);
        facultyDAO.update(faculty);

        System.out.println(faculty + " - was updated in DB!");
    }

    public void deleteFaculty(Faculty faculty) {
        facultyDAO.delete(faculty);

        System.out.println(faculty + " - was deleted from DB!");
    }
}
