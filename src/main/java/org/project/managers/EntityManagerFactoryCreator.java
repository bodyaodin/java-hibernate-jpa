package org.project.managers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryCreator {

    private static final String PERSISTENCE_CONFIG_NAME = "PersistenceConfig";

    private static EntityManagerFactory instance;

    private EntityManagerFactoryCreator() {
    }

    public static EntityManagerFactory getInstance() {
        if (instance == null) {
            instance = Persistence.createEntityManagerFactory(PERSISTENCE_CONFIG_NAME);
        }
        return instance;
    }
}
