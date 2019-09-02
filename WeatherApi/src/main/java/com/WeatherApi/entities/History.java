package com.WeatherApi.entities;

import java.util.Date;

public class History {

	private Date date;
	private double temp;

	public History() {
		super();
	}

	public History(City city) {
		super();
		this.date = city.getDate();
		this.temp = city.getMain().getTemp();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTemp() {
		return temp;
	}

	public void setTemp(double temp) {
		this.temp = temp;
	}

}
