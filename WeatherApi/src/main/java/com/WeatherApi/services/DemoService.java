package com.WeatherApi.services;

import org.springframework.security.access.annotation.Secured;
//Pomocna klasa za testiranje
public class DemoService {
	
	@Secured("USER")
    public void method()
    {
        System.out.println("Method called");
    }
}
