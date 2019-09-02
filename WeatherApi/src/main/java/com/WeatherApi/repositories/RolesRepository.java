package com.WeatherApi.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.WeatherApi.entities.Roles;

@Repository
public interface RolesRepository extends MongoRepository<Roles, String>{

	public Roles findByRole(String role);
}
