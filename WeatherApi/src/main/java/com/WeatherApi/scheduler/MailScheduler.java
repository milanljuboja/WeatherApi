package com.WeatherApi.scheduler;

import javax.mail.MessagingException;

public interface MailScheduler {
	
	public void sendMailToSubs() throws MessagingException;
}
