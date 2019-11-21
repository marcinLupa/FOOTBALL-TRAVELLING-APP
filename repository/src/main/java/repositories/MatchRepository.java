package repositories;

import dto.MatchDto;
import dto.TeamDto;
import generic.GenericRepository;
import model.football.Match;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface MatchRepository extends GenericRepository<MatchDto> {
    Optional<MatchDto> findByName(LocalDate gameTime, String home, String away);

}
