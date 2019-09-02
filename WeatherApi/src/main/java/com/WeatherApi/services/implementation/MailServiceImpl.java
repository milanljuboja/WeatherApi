package com.WeatherApi.services.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.WeatherApi.entities.City;
import com.WeatherApi.entities.Subscriptions;
import com.WeatherApi.entities.User;
import com.WeatherApi.entities.UserCity;
import com.WeatherApi.entities.WeatherForSubs;
import com.WeatherApi.repositories.CityRepository;
import com.WeatherApi.repositories.UserRepository;
import com.WeatherApi.services.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public void sendMailToSubs() throws MessagingException {
		
		for(UserCity uc: connectionBetweenUserAndCity(1)) {
			sendMail(uc.getEmail(), weatherToBeSentToSubs(uc).toString());
		}
		
		List<Integer> allTimes = showAllTimesFromSubs();
		int counter = lcmOfAllTimes(allTimes);
		int i = 2;
		
		if(counter > 1) {
			for(int j=0;j<allTimes.size();j++) {
				if(allTimes.get(j) % i == 0) {
					for(UserCity uc: connectionBetweenUserAndCity(allTimes.get(j))) {
						sendMail(uc.getEmail(), weatherToBeSentToSubs(uc).toString());
					}
				}
			}
		}
		
		i++;
		if(i>counter) {
			i=2;
		}
	}
	
	@Override
	public void sendMail(String to, String text) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("mailodvremenskeprognoze@asd.com");
		helper.setTo(to);
		helper.setSubject("Vremenska Prognoza");
		helper.setText(text);

		emailSender.send(message);
	}

	@Override
	public List<UserCity> connectionBetweenUserAndCity(int time) {

		List<User> listOfUsers = userRepository.findAll();
		List<UserCity> listUserCity = new ArrayList<>();

		for (User u : listOfUsers) {
			List<Subscriptions> subscriptions = u.getSubscriptions();
			UserCity userCity = new UserCity();
			userCity.setEmail(u.getEmail());
			List<String> namesOfCities = new ArrayList<>();

			for (Subscriptions s : subscriptions) {
				if (s.getInTime() == time) {
					namesOfCities.add(s.getCityName());
				}
			}
			userCity.setCitieNames(namesOfCities);
			listUserCity.add(userCity);
		}

		return listUserCity;
	}

	@Override
	public List<WeatherForSubs> weatherToBeSentToSubs(UserCity userCity) {

		List<WeatherForSubs> listOfWeathers = new ArrayList<>();
		for (String s : userCity.getCitieNames()) {
			City city = cityRepository.findByName(s);
			WeatherForSubs weather = new WeatherForSubs();
			weather.setCity(city);
			weather.setName(city.getName());
			weather.setTemp(city.getMain().getTemp());
			weather.setTemp_max(city.getMain().getTemp_max());
			weather.setTemp_min(city.getMain().getTemp_min());
			listOfWeathers.add(weather);
		}

		return listOfWeathers;
	}
	
	@Override
	public List<Integer> showAllTimesFromSubs(){
		
		List<User> users = userRepository.findAll();
		HashSet<Integer> allTimes = new HashSet<>();
		
		for(User u : users) {
			for(Subscriptions s: u.getSubscriptions()) {
				allTimes.add((int) s.getInTime());
			}
		}
		
		List<Integer> listOfTimes = new ArrayList<>(allTimes);
		
		return listOfTimes;
	}
	
	// Najmanji zajednicki sadrzalac na engleskom Least common multiple
	@Override
	public Integer lcmOfAllTimes(List<Integer> list) {
		
		int min=0;
		int max=0;
		int lcm=0;
		int x=0;
		
		for(int i=0;i<list.size();i++) {
			for(int j=1;j<list.size()-1;j++) {
				if(list.get(i)>list.get(j)) {
					max=list.get(i);
					min=list.get(j);
				}
				else {
					max=list.get(j);
					min=list.get(i);
				}
				for(int k=0;k<list.size();k++) {
					x=k*max;
					if(x%min==0) {
						lcm=x;
					}
				}
			}
		}
		return lcm;
	}
}
