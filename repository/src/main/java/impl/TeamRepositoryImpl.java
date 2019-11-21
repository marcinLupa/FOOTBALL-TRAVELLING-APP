package impl;

import dto.TeamDto;
import exceptions.MyException;
import generic.AbstractGenericRepository;
import repositories.TeamRepository;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class TeamRepositoryImpl extends AbstractGenericRepository<TeamDto> implements TeamRepository {
    @Override
    public Optional<TeamDto> findByName(String teamName) {

        Optional<TeamDto> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {

            entityTransaction.begin();
            List<TeamDto> airlineDtos = entityManager
                    .createQuery("select t from TeamDto t where t.name=:name", TeamDto.class)
                    .setParameter("name", teamName)
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
            throw new MyException("FIND BY NAME EXCEPTION");
        }
    }
}

