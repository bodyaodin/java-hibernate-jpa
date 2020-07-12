package org.project.services;

import org.project.dao.DAO;
import org.project.entities.Session;
import org.project.entities.Student;
import org.project.entities.Subject;
import org.project.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public class SessionService {

    private final DAO<Session> sessionDAO;

    public SessionService(DAO<Session> sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    public Session getSession(int id) {
        return sessionDAO.get(id).orElseThrow(() -> new EntityNotFoundException("There is no such entity in DB!"));
    }

    public List<Session> getAllSessions() {
        return sessionDAO.getAll();
    }

    public void saveSession(Session newSession) {
        sessionDAO.save(newSession);

        System.out.println(newSession + " - was saved to DB!");
    }

    public void updateSession(Session session, LocalDate newExamDate, int newSemester, Student newStudent, Subject newSubject, int newGrade) {
        session.setExamDate(newExamDate);
        session.setSemester(newSemester);
        session.setSubject(newSubject);
        session.setGrade(newGrade);
        session.getStudent().removeSession(session);
        newStudent.addSession(session);

        sessionDAO.update(session);

        System.out.println(session + " - was updated in DB!");
    }

    public void deleteSession(Session session) {
        String sessionInfo = session.toString();
        session.getStudent().removeSession(session);
        sessionDAO.delete(session);

        System.out.println(sessionInfo + " - was deleted from DB!");
    }
}
