package generic;

import connection.DbConnection;
import exceptions.MyException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    protected final EntityManager entityManager = DbConnection.getInstance().getEntityManager();

    private final Class<T> type = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public Optional<T> addOrUpdate(T t) {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            Optional<T> op = Optional.empty();
            entityTransaction.begin();
            op = Optional.of((T) entityManager.merge(t));
      //      entityManager.persist(t);
            entityTransaction.commit();


            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            throw new MyException("ADD OR UPDATE EXCEPTION - REPOSITORY");
        }
    }

    @Override
    public void delete(Long id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            T t = entityManager.find(type, id);
            entityManager.remove(t);
            entityTransaction.commit();


        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new MyException("DELETE EXCEPTION - REPOSITORY");
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            if (id == null) {
                throw new MyException("FIND BY ID EXCEPTION - REPOSITORY");
            }
            Optional<T> op = Optional.empty();
            entityTransaction.begin();
            op = Optional.of((T) entityManager.find(type, id));
            entityTransaction.commit();

            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new MyException("FIND BY ID EXCEPTION - REPOSITORY");
        }
    }

    @Override
    public List<T> findAll() {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {

            List<T> elements = new ArrayList<>();
            entityTransaction.begin();
            System.out.println(entityManager
                    .createQuery("select t from " + type.getCanonicalName() + " t", type));
            elements = entityManager
                    .createQuery("select t from " + type.getCanonicalName() + " t", type)
                    .getResultList();
            entityTransaction.commit();

            return elements;

        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new MyException("FIND ALL EXCEPTION - REPOSITORY");
        }
    }
}