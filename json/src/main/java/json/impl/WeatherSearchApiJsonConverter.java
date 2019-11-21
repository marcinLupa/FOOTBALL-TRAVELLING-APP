package json.impl;

import json.converters.WeatherSearchApiConverter;
import json.generic.ApiJsonConverter;
import model.weather.ListOfWeatherFromApi;

public class WeatherSearchApiJsonConverter extends ApiJsonConverter<ListOfWeatherFromApi> implements WeatherSearchApiConverter {
}
