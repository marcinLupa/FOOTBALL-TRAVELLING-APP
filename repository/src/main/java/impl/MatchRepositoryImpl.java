package impl;

import dto.AirlineDto;
import dto.MatchDto;
import dto.TeamDto;
import exceptions.MyException;
import generic.AbstractGenericRepository;
import generic.GenericRepository;
import model.football.Match;
import repositories.MatchRepository;

import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class MatchRepositoryImpl extends AbstractGenericRepository<MatchDto> implements MatchRepository {
    @Override
    public Optional<MatchDto> findByName(LocalDate gameTime, String home, String away) {

            Optional<MatchDto> op = Optional.empty();
            EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                List<MatchDto> matchDtos = entityManager
                        .createQuery("select m from MatchDto m where m.dateOfMatch=:date AND m.awayTeam.name=:team1 AND m.homeTeam.name=:team2 ", MatchDto.class)
                        .setParameter("date", gameTime)
                        .setParameter("team1", home)
                        .setParameter("team2", away)
                        .getResultList();

                if (!matchDtos.isEmpty()) {
                    op = Optional.of(matchDtos.get(0));
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

