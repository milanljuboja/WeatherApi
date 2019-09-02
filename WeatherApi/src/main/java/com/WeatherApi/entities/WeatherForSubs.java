package com.WeatherApi.entities;

public class WeatherForSubs {

	private City city;
	private String name;
	private double temp;
	private double temp_min;
	private double temp_max;

	public WeatherForSubs() {
		super();
	}

	public WeatherForSubs(City city, String name, double temp, double temp_min, double temp_max) {
		super();
		this.city = city;
		this.name = name;
		this.temp = temp;
		this.temp_min = temp_min;
		this.temp_max = temp_max;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

	public double getTemp_min() {
		return temp_min;
	}

	public void setTemp_min(double temp_min) {
		this.temp_min = temp_min;
	}

	public double getTemp_max() {
		return temp_max;
	}

	public void setTemp_max(double temp_max) {
		this.temp_max = temp_max;
	}

}
