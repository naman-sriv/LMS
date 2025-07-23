package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtility {

    private static EntityManagerFactory entityManagerFactory;

    private JpaUtility() {}

    public static void init() {
        try{
            if(entityManagerFactory==null || !entityManagerFactory.isOpen()){
                entityManagerFactory = Persistence.createEntityManagerFactory("library-pu");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e.getMessage());
        }

    }

    public static EntityManager getEntityManager() {
        if(entityManagerFactory==null || !entityManagerFactory.isOpen()){
            System.out.println("Manager not initialized");
        }
        return entityManagerFactory.createEntityManager();
    }

    public static void shutdown() {
        if(entityManagerFactory!=null && entityManagerFactory.isOpen()){
            entityManagerFactory.close();
        }
    }
}
