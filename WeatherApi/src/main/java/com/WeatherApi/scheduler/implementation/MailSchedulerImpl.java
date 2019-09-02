package com.WeatherApi.scheduler.implementation;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.WeatherApi.scheduler.MailScheduler;
import com.WeatherApi.services.MailService;

@Component
public class MailSchedulerImpl implements MailScheduler {

	@Autowired
	private MailService mailService;
	
	//@Scheduled(fixedRate = 1000)
	@Override
	public void sendMailToSubs() throws MessagingException {
		mailService.sendMailToSubs();
	}

}
