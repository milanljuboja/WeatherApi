package com.WeatherApi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.WeatherApi.entities.City;

@Repository
public interface CityRepository extends MongoRepository<City, String>{
	
	public City findByName(String name);
}
