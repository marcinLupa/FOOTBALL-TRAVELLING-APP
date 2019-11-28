package impl;
import dto.CityDto;
import exceptions.MyException;
import generic.AbstractGenericRepository;
import repositories.CityRepository;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class CityRepositoryImpl extends AbstractGenericRepository<CityDto> implements CityRepository {
    @Override
    public Optional<CityDto> findByName(String cityName) {

        Optional<CityDto> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {

            entityTransaction.begin();
            List<CityDto> cityDtos = entityManager
                    .createQuery("select c from CityDto c where c.cityName=:name", CityDto.class)
                    .setParameter("name", cityName)
                    .getResultList();

            if (!cityDtos.isEmpty()) {
                op = Optional.of(cityDtos.get(0));
            }
            entityTransaction.commit();
            return op;
        } catch (Exception e) {
            e.printStackTrace();
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new MyException( "FIND BY NAME EXCEPTION");
        }
    }
}
