package com.WeatherApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.WeatherApi.dto.AuthBody;
import com.WeatherApi.dto.UserDTO;
import com.WeatherApi.entities.Subscriptions;
import com.WeatherApi.entities.User;
import com.WeatherApi.exceptions.ResourceNotFoundException;
import com.WeatherApi.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/")
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthBody data) {
		return userService.login(data);
	}
	
	@PutMapping("/update/{id}")
	public User updateUser(@PathVariable(name = "id") String id, @RequestBody UserDTO userDTO) {
		return userService.updateUser(id, userDTO);
	}

	@DeleteMapping("/delete/{id}")
	public void deleteUser(@PathVariable(name = "id") String id) {
		userService.deleteUser(id);
	}

	@PutMapping("/addSubscription/{email}")
	public User addSubscription(@PathVariable(name = "email") String email, 
			@RequestBody Subscriptions subscription) {
		return userService.addSubscription(email, subscription);
	}
	
	@DeleteMapping("/deleteSubscription/{email}")
	public User deleteSubscription(@PathVariable(name = "email") String email, 
			@RequestBody Subscriptions subscription) {
		return userService.deleteSubscription(email, subscription);
	}
	
	@GetMapping("/{username}")
	public User findUserByUsername(@PathVariable(name = "username") String username) throws ResourceNotFoundException {
		return userService.findUserByUsername(username);
	}
}
