package json.converters;

import json.generic.GenericConverter;
import model.skyScanner.CityFromApi;
import model.weather.Weather;

import java.util.List;
import java.util.Map;

public interface MapCityListWeatherConverter extends GenericConverter<Map<CityFromApi, List<Weather>>> {
}
