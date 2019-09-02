package com.WeatherApi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.WeatherApi.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
	
	public User findByEmail(String email);
	public User findByUsername(String username);
}
