package org.project.dao;

import org.project.entities.Faculty;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class FacultyDAOImpl implements DAO<Faculty> {

    private final EntityManagerFactory entityManagerFactory;

    public FacultyDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Faculty> get(int id) {
        String getQuery = "SELECT f FROM Faculty f " +
                "left join fetch f.chairs " +
                "where f.id = :id";

        return Optional.ofNullable(executeInsideEntityManager(entityManager ->
                entityManager
                        .createQuery(getQuery, Faculty.class)
                        .setParameter("id", id)
                        .getSingleResult()));
    }

    @Override
    public List<Faculty> getAll() {
        String getQuery = "SELECT f FROM Faculty f";

        return executeInsideEntityManager(entityManager ->
                entityManager.createQuery(getQuery, Faculty.class).getResultList());
    }

    @Override
    public void save(Faculty faculty) {
        executeInsideTransaction(entityManager -> entityManager.persist(faculty));
    }

    @Override
    public void update(Faculty faculty) {
        executeInsideTransaction(entityManager -> entityManager.merge(faculty));
    }

    @Override
    public void delete(Faculty faculty) {
        executeInsideTransaction(entityManager ->
                entityManager.remove(entityManager.contains(faculty) ? faculty : entityManager.merge(faculty)));
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
