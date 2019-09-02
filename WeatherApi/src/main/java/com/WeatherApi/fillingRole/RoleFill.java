package com.WeatherApi.fillingRole;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.WeatherApi.entities.Roles;
import com.WeatherApi.repositories.RolesRepository;

public class RoleFill {
	
	@Bean
	CommandLineRunner init(RolesRepository rolesRepository) {

	    return args -> {

	        Roles adminRole = rolesRepository.findByRole("ADMIN");
	        if (adminRole == null) {
	            Roles newAdminRole = new Roles();
	            newAdminRole.setRole("ADMIN");
	            rolesRepository.save(newAdminRole);
	        }
	    };

	}
	
}
