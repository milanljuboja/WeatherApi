package com.WeatherApi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WeatherApi.dto.CityDTO;
import com.WeatherApi.entities.City;
import com.WeatherApi.services.CityService;

@RestController
@RequestMapping("/city")
public class CityController {

	@Autowired
	private CityService cityService;

	@GetMapping("/saveViaBody")
	public City saveCityViaBody(@Valid @RequestBody CityDTO cityDTO) {
		return cityService.saveCityViaBody(cityDTO);
	}
	
	@GetMapping("/save/{cityName}")
	public City saveCity(@PathVariable(name = "cityName") String cityName) {
		return cityService.saveCity(cityName);
	}
	
	@GetMapping("/findAll")
	public List<City> findAllCities(){
		return cityService.findAllCities();
	}
	
	@PutMapping("/updateAllCities")
	public List<City> updateAllCities(){
		return cityService.updateAllCities();
	}
	
	@GetMapping("/{cityName}")
	public City findCityByName(@PathVariable(name = "cityName") String cityName) {
		return cityService.findCityByName(cityName);
	}
	
	@GetMapping("/findCityByCoordinates/{lat}/{lon}")
	public City findByCoordinates(@PathVariable(name = "lat") String lat, 
			@PathVariable(name = "lon") String lon) {
		return cityService.findCityByCoordinates(lat, lon);
	}
	
	@GetMapping("/findCityByZipCode/{zip}")
	public City findCityByZipCode(@PathVariable(name = "zip") String zip) {
		return cityService.findCityByZipCode(zip);
	}
}
