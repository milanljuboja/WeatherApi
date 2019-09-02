package com.WeatherApi.entities;

public class Subscriptions {

	private long inTime;
	private String cityName;

	public Subscriptions() {
		super();
	}

	public Subscriptions(long inTime, String cityName) {
		super();
		this.inTime = inTime;
		this.cityName = cityName;
	}

	public long getInTime() {
		return inTime;
	}

	public void setInTime(long inTime) {
		this.inTime = inTime;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
