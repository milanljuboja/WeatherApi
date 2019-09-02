package com.WeatherApi.services;

import org.springframework.http.ResponseEntity;

import com.WeatherApi.dto.AuthBody;
import com.WeatherApi.dto.UserDTO;
import com.WeatherApi.entities.Subscriptions;
import com.WeatherApi.entities.User;
import com.WeatherApi.exceptions.ResourceNotFoundException;

public interface UserService {
	
	public User createUser(User user);
	public User register(User user);
	public User updateUser(String id, UserDTO userDTO);
	public void deleteUser(String id);
	public User addSubscription(String email, Subscriptions subscription);
	public User deleteSubscription(String email, Subscriptions subscription);
	public User findUserByUsername(String username) throws ResourceNotFoundException;
	public ResponseEntity<?> login(AuthBody data);
}
