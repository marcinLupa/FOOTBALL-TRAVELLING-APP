package repositories;

import dto.CityDto;
import generic.GenericRepository;

import java.util.Optional;

public interface CityRepository extends GenericRepository<CityDto> {
    Optional<CityDto> findByName(String airlineName);

}
