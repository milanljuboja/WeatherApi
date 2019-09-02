package com.WeatherApi.services.implementation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.WeatherApi.dto.CityDTO;
import com.WeatherApi.entities.City;
import com.WeatherApi.entities.History;
import com.WeatherApi.properties.RestProperties;
import com.WeatherApi.repositories.CityRepository;
import com.WeatherApi.services.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private RestProperties restProperties;
	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public City saveCityViaBody(CityDTO cityDTO) {
		
		List<History> listOfHistories = new ArrayList<>();
		History history = new History();
		
		if(cityRepository.findByName(cityDTO.getName()) != null) {
			City city = cityRepository.findByName(cityDTO.getName());
			
			history.setDate(new Date());
			history.setTemp(city.getMain().getTemp());
			
			if(city.getHistory() != null)
				listOfHistories.addAll(city.getHistory());
			listOfHistories.add(history);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(restProperties.keyName, restProperties.keyValue);
		String url = restProperties.theUrl + cityDTO.getName();
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<City> response = restTemplate.exchange(url, HttpMethod.GET, entity, City.class);
		City cityToBeSaved = response.getBody();
		cityToBeSaved.setDate(new Date());
		cityToBeSaved.setHistory(listOfHistories);
		
		return cityRepository.save(cityToBeSaved);
	}
	
	@Override
	public City saveCity(String cityName) {
		
		List<History> listOfHistories = new ArrayList<>();
		History history = new History();
		
		if(cityRepository.findByName(cityName) != null) {
			City city = cityRepository.findByName(cityName);
			
			history.setDate(new Date());
			history.setTemp(city.getMain().getTemp());
			
			if(city.getHistory() != null)
				listOfHistories.addAll(city.getHistory());
			listOfHistories.add(history);
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(restProperties.keyName, restProperties.keyValue);
		String url = restProperties.theUrl + cityName;
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<City> response = restTemplate.exchange(url, HttpMethod.GET, entity, City.class);
		City cityToBeSaved = response.getBody();
		cityToBeSaved.setDate(new Date());
		cityToBeSaved.setHistory(listOfHistories);
		
		return cityRepository.save(cityToBeSaved);
	}
	
	@Override
	public City findCityByName(String cityName) {
		return cityRepository.findByName(cityName);
	}
	
	@Override
	public City findCityByCoordinates(String lat, String lon) {
		
		String url = "https://samples.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid=" + restProperties.keyValue;
		
		City city = restTemplate.getForObject(url, City.class, lat, lon);
		return city;
	}
	
	@Override
	public City findCityByZipCode(String zip) {
		
		String url = "https://samples.openweathermap.org/data/2.5/weather?zip={zip}&appid=" + restProperties.keyValue;
		
		City city = restTemplate.getForObject(url, City.class, zip);
		return city;

	}
	
	@Override
	public List<City> findAllCities() {
		List<City> listOfCities = new ArrayList<City>();
		for (City c : cityRepository.findAll()) {
			listOfCities.add(c);
		}
		return listOfCities;
	}

	@Override
	public List<City> updateAllCities() {
		
		List<City> allUpdatedCities = new ArrayList<>();
		
		for(City c: cityRepository.findAll()) {
			allUpdatedCities.add(saveCity(c.getName()));
		}
		return allUpdatedCities;
	}
}
