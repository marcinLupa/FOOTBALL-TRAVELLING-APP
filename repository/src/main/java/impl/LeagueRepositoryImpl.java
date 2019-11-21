package impl;

import dto.LeagueDto;
import dto.MatchDto;
import exceptions.MyException;
import generic.AbstractGenericRepository;
import repositories.LeagueRepository;

import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class LeagueRepositoryImpl extends AbstractGenericRepository<LeagueDto> implements LeagueRepository {
    @Override
    public Optional<LeagueDto> findByName(String countryName) {
        Optional<LeagueDto> op = Optional.empty();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            List<LeagueDto> matchDtos = entityManager
                    .createQuery("select l from LeagueDto l where l.country.name=:name ", LeagueDto.class)
                    .setParameter("name", countryName)
                    .getResultList();

            if (!matchDtos.isEmpty()) {
                op = Optional.of(matchDtos.get(0));
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
