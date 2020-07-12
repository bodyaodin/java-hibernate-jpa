package org.project.dao;

import org.project.entities.Subject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class SubjectDAOImpl implements DAO<Subject> {

    private final EntityManagerFactory entityManagerFactory;

    public SubjectDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Subject> get(int id) {
        return Optional.ofNullable(executeInsideEntityManager(entityManager ->
                entityManager.find(Subject.class, id)));
    }

    @Override
    public List<Subject> getAll() {
        String getQuery = "SELECT sub FROM Subject sub";

        return executeInsideEntityManager(entityManager ->
                entityManager.createQuery(getQuery, Subject.class).getResultList());
    }

    @Override
    public void save(Subject subject) {
        executeInsideTransaction(entityManager -> entityManager.persist(subject));
    }

    @Override
    public void update(Subject subject) {
        executeInsideTransaction(entityManager -> entityManager.merge(subject));
    }

    @Override
    public void delete(Subject subject) {
        executeInsideTransaction(entityManager ->
                entityManager.remove(entityManager.contains(subject) ? subject : entityManager.merge(subject)));
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
