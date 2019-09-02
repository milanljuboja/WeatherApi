package com.WeatherApi;

import static org.junit.Assert.assertEquals;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.WeatherApi.entities.City;
import com.WeatherApi.security.securityService.CustomUserDetailsService;
import com.WeatherApi.services.DemoService;

@RunWith(SpringJUnit4ClassRunner.class)
public class CityControllerTest {

	TestRestTemplate testRestTemplate = new TestRestTemplate();
	private Logger logger = Logger.getLogger(getClass().getName());

	@Test
	public void testGetWeatherByGeoIfOk() {

		ResponseEntity<City> response = testRestTemplate.withBasicAuth("fgh", "fgh")
				.getForEntity("http://localhost:8080/city/35/139", City.class);

		logger.info("Response entity status code is: " + response.getStatusCode());

		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	static ClassPathXmlApplicationContext applicationContext = null;
	static CustomUserDetailsService userDetailsService = null;

	@BeforeClass
	public static void setup() {
		applicationContext = new ClassPathXmlApplicationContext();
		userDetailsService = applicationContext.getBean(CustomUserDetailsService.class);
	}

	@Test
	public void testValidRole() {
		UserDetails userDetails = userDetailsService.loadUserByUsername("janko");
		Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
				userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
		DemoService service = (DemoService) applicationContext.getBean("demoService");
		service.method();
	}
	
	@Test (expected = AccessDeniedException.class)
    public void testInvalidRole()
    {
        UserDetails userDetails = userDetailsService.loadUserByUsername ("janko");
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);
        DemoService service = (DemoService) applicationContext.getBean("demoService");
        service.method();
    }
}
