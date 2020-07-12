package org.project.dao;

import org.project.entities.Chair;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ChairDAOImpl implements DAO<Chair> {

    private final EntityManagerFactory entityManagerFactory;

    public ChairDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Chair> get(int id) {
        String getQuery = "SELECT ch FROM Chair ch " +
                "join fetch ch.faculty " +
                "left join fetch ch.studentGroups " +
                "where ch.id = :id";

        return Optional.ofNullable(executeInsideEntityManager(entityManager ->
                entityManager
                        .createQuery(getQuery, Chair.class)
                        .setParameter("id", id)
                        .getSingleResult()));
    }

    @Override
    public List<Chair> getAll() {
        String getQuery = "SELECT ch FROM Chair ch" +
                "join fetch ch.faculty";

        return executeInsideEntityManager(entityManager ->
                entityManager.createQuery(getQuery, Chair.class).getResultList());
    }

    @Override
    public void save(Chair chair) {
        executeInsideTransaction(entityManager -> entityManager.persist(chair));
    }

    @Override
    public void update(Chair chair) {
        executeInsideTransaction(entityManager -> entityManager.merge(chair));
    }

    @Override
    public void delete(Chair chair) {
        executeInsideTransaction(entityManager ->
                entityManager.remove(entityManager.contains(chair) ? chair : entityManager.merge(chair)));
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
