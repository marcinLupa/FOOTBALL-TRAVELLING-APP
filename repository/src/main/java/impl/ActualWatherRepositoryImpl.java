package impl;

import dto.ActualWeatherDto;
import dto.AirlineDto;
import dto.CityDto;
import exceptions.MyException;
import generic.AbstractGenericRepository;
import repositories.ActualWeatherRepository;

import javax.persistence.EntityTransaction;
import java.util.Optional;

public class ActualWatherRepositoryImpl extends AbstractGenericRepository<ActualWeatherDto> implements ActualWeatherRepository {


    @Override
    public Optional<CityDto> cityMaxTemp() {
        Optional<Object[]> op = Optional.empty();
        Optional<CityDto> cityDto;
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            op = Optional.of(entityManager
                    .createQuery("select a.city,max(a.tempreture) from ActualWeatherDto a ", Object[].class)
                    .getSingleResult());
            cityDto = Optional.of((CityDto) op.orElseThrow()[0]);
            System.out.println(cityDto);
            entityTransaction.commit();
            return cityDto;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            e.printStackTrace();
            throw new MyException("FIND BY NAME EXCEPTION");
        }
    }
}
