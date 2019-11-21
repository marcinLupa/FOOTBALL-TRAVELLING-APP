package repositories;

import dto.AirlineDto;
import dto.LeagueDto;
import generic.GenericRepository;

import java.util.Optional;

public interface LeagueRepository extends GenericRepository<LeagueDto> {
    Optional<LeagueDto> findByName(String countryName);

}
