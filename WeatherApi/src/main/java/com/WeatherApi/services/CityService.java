package com.WeatherApi.services;

import java.util.List;

import com.WeatherApi.dto.CityDTO;
import com.WeatherApi.entities.City;

public interface CityService {

	public City saveCityViaBody(CityDTO cityDTO);
	public List<City> findAllCities();
	public List<City> updateAllCities();
	public City findCityByName(String cityName);
	public City findCityByCoordinates(String lat, String lon);
	public City findCityByZipCode(String zip);
	public City saveCity(String cityName);
}
