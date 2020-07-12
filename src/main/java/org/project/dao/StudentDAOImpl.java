package org.project.dao;

import org.project.entities.Chair;
import org.project.entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class StudentDAOImpl implements DAO<Student> {

    private final EntityManagerFactory entityManagerFactory;

    public StudentDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Student> get(int id) {
        String getQuery = "SELECT s FROM Student s " +
                "join fetch s.studentGroup " +
                "left join fetch s.sessions " +
                "where s.id = :id";

        return Optional.ofNullable(executeInsideEntityManager(entityManager ->
                entityManager
                        .createQuery(getQuery, Student.class)
                        .setParameter("id", id)
                        .getSingleResult()));
    }

    @Override
    public List<Student> getAll() {
        String getQuery = "SELECT s FROM Student s " +
                "join fetch s.studentGroup";

        return executeInsideEntityManager(entityManager ->
                entityManager.createQuery(getQuery, Student.class).getResultList());
    }

    @Override
    public void save(Student student) {
        executeInsideTransaction(entityManager -> entityManager.persist(student));
    }

    @Override
    public void update(Student student) {
        executeInsideTransaction(entityManager -> entityManager.merge(student));
    }

    @Override
    public void delete(Student student) {
        executeInsideTransaction(entityManager ->
                entityManager.remove(entityManager.contains(student) ? student : entityManager.merge(student)));
    }

    private <R> R executeInsideEntityManager(Function<EntityManager, R> action) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            return action.apply(entityManager);
        } finally {
            entityManager.close();
        }
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            action.accept(entityManager);
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
