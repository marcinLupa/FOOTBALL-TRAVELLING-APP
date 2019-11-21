package impl;

import dto.AirlineDto;
import exceptions.MyException;
import generic.AbstractGenericRepository;
import repositories.AirlineRepository;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class AirlineRepositoryImpl extends AbstractGenericRepository<AirlineDto> implements AirlineRepository {

    @Override
    public Optional<AirlineDto> findByName(String airlineName) {

        Optional<AirlineDto> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {

            entityTransaction.begin();
            List<AirlineDto> airlineDtos = entityManager
                    .createQuery("select a from AirlineDto a where a.carrierName=:name", AirlineDto.class)
                    .setParameter("name", airlineName)
                    .getResultList();

            if (!airlineDtos.isEmpty()) {
                op = Optional.of(airlineDtos.get(0));
            }

            entityTransaction.commit();
            return op;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new MyException( "FIND BY NAME EXCEPTION");
        }
    }
}
