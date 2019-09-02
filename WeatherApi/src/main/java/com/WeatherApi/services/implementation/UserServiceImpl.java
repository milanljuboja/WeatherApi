package com.WeatherApi.services.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.WeatherApi.dto.AuthBody;
import com.WeatherApi.dto.UserDTO;
import com.WeatherApi.entities.City;
import com.WeatherApi.entities.Subscriptions;
import com.WeatherApi.entities.User;
import com.WeatherApi.exceptions.ResourceNotFoundException;
import com.WeatherApi.repositories.CityRepository;
import com.WeatherApi.repositories.UserRepository;
import com.WeatherApi.security.configuration.JwtTokenProvider;
import com.WeatherApi.security.securityService.CustomUserDetailsService;
import com.WeatherApi.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User register(User user) {
		User userExists = userDetailsService.findByUsername(user.getUsername());
        if (userExists != null) {
            throw new BadCredentialsException("User with username: " + user.getUsername() + " already exists");
        }
		return userDetailsService.saveUser(user);
	}
	
	@Override
	public User findUserByUsername(String username) throws ResourceNotFoundException  {
		User user = userRepository.findByUsername(username);
		if (user == null)
			throw new ResourceNotFoundException("User not found with username: " +username);
		return user;
	}
	
	@Override
	public User updateUser(String id, UserDTO userDTO) {
		User updatedUser = userRepository.findById(id).get();
		updatedUser.setEmail(userDTO.getEmail());
		updatedUser.setUsername(userDTO.getUsername());
		updatedUser.setPassword(userDTO.getPassword());

		return userRepository.save(updatedUser);
	}

	@Override
	public void deleteUser(String id) {
		userRepository.delete(userRepository.findById(id).get());
	}

	@Override
	public User addSubscription(String email, Subscriptions subscription) {

		User user = userRepository.findByEmail(email);

		for (City c : cityRepository.findAll()) {
			if (c.getName().equals(subscription.getCityName())) {

				List<Subscriptions> subscriptions = new ArrayList<>();
				boolean subscriptionRepetition = false;

				if (user.getSubscriptions() != null) {
					subscriptions = user.getSubscriptions();
					for (Subscriptions s : subscriptions) {
						if (s.getCityName().equals(subscription.getCityName())
								&& s.getInTime() == subscription.getInTime()) {
							subscriptionRepetition = true;
							break;
						}
					}
					if (subscriptionRepetition == false) {
						subscriptions.add(subscription);
						user.setSubscriptions(subscriptions);
						return userRepository.save(user);
					} else
						return user;
				} else {
					subscriptions.add(subscription);
					user.setSubscriptions(subscriptions);
					return userRepository.save(user);
				}
			}
		}
		return user;
	}

	@Override
	public User deleteSubscription(String email, Subscriptions subscription) {

		User user = userRepository.findByEmail(email);
		List<Subscriptions> subscriptions = new ArrayList<>();
		List<Subscriptions> newSubscriptions = new ArrayList<>();

		if (user.getSubscriptions() != null) {
			subscriptions = user.getSubscriptions();
			for (Subscriptions s : subscriptions) {
				if (!(s.getCityName().equals(subscription.getCityName())
						&& s.getInTime() == subscription.getInTime())) {

					newSubscriptions.add(s);
				}
			}
			user.setSubscriptions(newSubscriptions);
			return userRepository.save(user);
		} else
			return user;
	}

	@Override
	public ResponseEntity<?> login(AuthBody data) {
		try {
            String username = data.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.userRepository.findByUsername(username).getRoles());
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
	}
}
