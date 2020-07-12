package org.project.dao;

import org.project.entities.Chair;
import org.project.entities.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class SessionDAOImpl implements DAO<Session> {

    private final EntityManagerFactory entityManagerFactory;

    public SessionDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<Session> get(int id) {
        String getQuery = "SELECT ses FROM Session ses " +
                "join fetch ses.student " +
                "join fetch ses.subject " +
                "where ses.id = :id";

        return Optional.ofNullable(executeInsideEntityManager(entityManager ->
                entityManager
                        .createQuery(getQuery, Session.class)
                        .setParameter("id", id)
                        .getSingleResult()));
    }

    @Override
    public List<Session> getAll() {
        String getQuery = "SELECT ses FROM Session ses " +
                "join fetch ses.student " +
                "join fetch ses.subject";

        return executeInsideEntityManager(entityManager ->
                entityManager.createQuery(getQuery, Session.class).getResultList());
    }

    @Override
    public void save(Session session) {
        executeInsideTransaction(entityManager -> entityManager.persist(session));
    }

    @Override
    public void update(Session session) {
        executeInsideTransaction(entityManager -> entityManager.merge(session));
    }

    @Override
    public void delete(Session session) {
        executeInsideTransaction(entityManager ->
                entityManager.remove(entityManager.contains(session) ? session : entityManager.merge(session)));
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
