package json.impl;

import json.converters.MapCityListWeatherConverter;
import json.generic.ApiJsonConverter;
import model.skyScanner.CityFromApi;
import model.weather.Weather;

import java.util.List;
import java.util.Map;

public class MapCityListWeatherConverterJsonApiConverter extends ApiJsonConverter<Map<CityFromApi, List<Weather>>>
        implements MapCityListWeatherConverter {
}
