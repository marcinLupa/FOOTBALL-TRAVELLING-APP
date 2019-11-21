package repositories;

import dto.AirlineDto;
import generic.GenericRepository;

import java.util.Optional;

public interface AirlineRepository extends GenericRepository<AirlineDto> {
    Optional<AirlineDto> findByName(String airlineName);

}
