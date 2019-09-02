package com.WeatherApi.entities;

import java.util.List;

public class UserCity {

	private String email;
	private List<String> citieNames;

	public UserCity() {
		super();
	}

	public UserCity(String email, List<String> citieNames) {
		super();
		this.email = email;
		this.citieNames = citieNames;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getCitieNames() {
		return citieNames;
	}

	public void setCitieNames(List<String> citieNames) {
		this.citieNames = citieNames;
	}

}
