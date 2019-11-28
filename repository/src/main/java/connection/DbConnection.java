package connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();

    public static DbConnection getInstance() {
        return ourInstance;
    }

    private DbConnection() {
    }
    private EntityManagerFactory entityManagerFactory=
            Persistence.createEntityManagerFactory("HIBERNATE_PROVIDER");

    public EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
    public void close(){
        if (entityManagerFactory.isOpen()){
            System.out.println("CLOSE DB-SMF");
            entityManagerFactory.close();
        }
    }
}