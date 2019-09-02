package com.WeatherApi.scheduler.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.WeatherApi.scheduler.CityScheduler;
import com.WeatherApi.services.CityService;

@Component
public class CitySchedulerImpl implements CityScheduler{
	
	@Autowired
	private CityService cityService;
	
//	@Scheduled(fixedRate = 2000)
	@Override
	public void updateCities() {
		cityService.updateAllCities();
	}
	
}
