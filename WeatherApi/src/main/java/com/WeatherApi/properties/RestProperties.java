package com.WeatherApi.properties;

import org.springframework.stereotype.Component;

@Component
public final class RestProperties {
	
	public String keyValue = "62402b233cc8f90e346427f2572369d4";
	public String theUrl = "https://api.openweathermap.org/data/2.5/weather?q=";
	public String keyName = "x-api-key";
}
