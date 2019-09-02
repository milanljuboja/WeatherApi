package com.WeatherApi.services;

import java.util.List;

import javax.mail.MessagingException;

import com.WeatherApi.entities.UserCity;
import com.WeatherApi.entities.WeatherForSubs;

public interface MailService {
	
	public List<UserCity> connectionBetweenUserAndCity(int time);
	public List<WeatherForSubs> weatherToBeSentToSubs(UserCity userCity);
	public void sendMail(String to, String text) throws MessagingException;
	public void sendMailToSubs() throws MessagingException;
	public List<Integer> showAllTimesFromSubs();
	public Integer lcmOfAllTimes(List<Integer> list);
}
