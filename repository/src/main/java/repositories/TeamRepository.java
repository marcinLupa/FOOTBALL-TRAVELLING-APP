package repositories;

import dto.AirlineDto;
import dto.TeamDto;
import generic.GenericRepository;

import java.util.Optional;

public interface TeamRepository extends GenericRepository<TeamDto> {

    Optional<TeamDto> findByName(String teamName);

}
