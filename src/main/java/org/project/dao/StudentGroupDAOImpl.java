package org.project.dao;

import org.project.entities.StudentGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class StudentGroupDAOImpl implements DAO<StudentGroup> {

    private final EntityManagerFactory entityManagerFactory;

    public StudentGroupDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Optional<StudentGroup> get(int id) {
        String getQuery = "SELECT sg FROM StudentGroup sg " +
                "join fetch sg.chair " +
                "left join fetch sg.students " +
                "where sg.id = :id";

        return Optional.ofNullable(executeInsideEntityManager(entityManager ->
                entityManager
                        .createQuery(getQuery, StudentGroup.class)
                        .setParameter("id", id)
                        .getSingleResult()));
    }

    @Override
    public List<StudentGroup> getAll() {
        String getQuery = "SELECT sg FROM StudentGroup sg " +
                "join fetch sg.chair";

        return executeInsideEntityManager(entityManager ->
                entityManager.createQuery(getQuery, StudentGroup.class).getResultList());
    }

    @Override
    public void save(StudentGroup studentGroup) {
        executeInsideTransaction(entityManager -> entityManager.persist(studentGroup));
    }

    @Override
    public void update(StudentGroup studentGroup) {
        executeInsideTransaction(entityManager -> entityManager.merge(studentGroup));
    }

    @Override
    public void delete(StudentGroup studentGroup) {
        executeInsideTransaction(entityManager ->
                entityManager.remove(entityManager.contains(studentGroup) ? studentGroup : entityManager.merge(studentGroup)));
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
