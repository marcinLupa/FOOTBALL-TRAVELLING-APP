package repositories;

import dto.ActualWeatherDto;
import dto.CityDto;
import generic.GenericRepository;

import java.util.Optional;

public interface ActualWeatherRepository extends GenericRepository<ActualWeatherDto> {
    Optional<CityDto> cityMaxTemp();
}
