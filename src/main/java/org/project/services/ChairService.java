package org.project.services;

import org.project.dao.DAO;
import org.project.entities.Chair;
import org.project.entities.Faculty;
import org.project.exceptions.EntityNotFoundException;

import java.util.List;

public class ChairService {

    private final DAO<Chair> chairDAO;

    public ChairService(DAO<Chair> chairDAO) {
        this.chairDAO = chairDAO;
    }

    public Chair getChair(int id) {
        return chairDAO.get(id).orElseThrow(() -> new EntityNotFoundException("There is no such entity in DB!"));
    }

    public List<Chair> getAllChairs() {
        return chairDAO.getAll();
    }

    public void saveChair(Chair newChair) {
        chairDAO.save(newChair);

        System.out.println(newChair + " - was saved to DB!");
    }

    public void updateChair(Chair chair, String newName, Faculty newFaculty) {
        chair.setChairName(newName);
        chair.getFaculty().removeChair(chair);
        newFaculty.addChair(chair);

        chairDAO.update(chair);

        System.out.println(chair + " - was updated in DB!");
    }

    public void deleteChair(Chair chair) {
        chair.getFaculty().removeChair(chair);
        chairDAO.delete(chair);

        System.out.println(chair + " - was deleted from DB!");
    }
}
