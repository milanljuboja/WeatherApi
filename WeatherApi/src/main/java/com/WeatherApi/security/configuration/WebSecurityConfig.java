package com.WeatherApi.security.configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.WeatherApi.security.securityService.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    UserDetailsService userDetailsService = mongoUserDetails();
	    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.httpBasic().disable().csrf().disable().sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
	            .antMatchers("/user/login").permitAll().antMatchers("/user/register").permitAll()
	            .antMatchers("/city/saveViaBody").hasAuthority("ADMIN").anyRequest().authenticated()
	            .antMatchers("/city/save/**").hasAuthority("ADMIN").anyRequest().authenticated()
	            .antMatchers("/city/updateAllCities").hasAuthority("ADMIN").anyRequest().authenticated()
	            .antMatchers("/city/{cityName}").hasAnyAuthority("ADMIN", "USER").anyRequest().authenticated()
	            .antMatchers("/city/findCityByCoordinates/**").hasAnyAuthority("ADMIN", "USER").anyRequest().authenticated()
	            .antMatchers("/city/findCityByZipCode/**").hasAnyAuthority("ADMIN", "USER").anyRequest().authenticated()
	            .antMatchers("/city/findAll").hasAnyAuthority("ADMIN", "USER").anyRequest().authenticated()
	            .antMatchers("user/update/**").hasAuthority("ADMIN").anyRequest().authenticated()
	            .antMatchers("/user/delete/**").hasAuthority("ADMIN").anyRequest().authenticated()
	            .antMatchers("/user/addSubscription/**").hasAuthority("ADMIN").anyRequest().authenticated()
	            .antMatchers("/deleteSubscription/**").hasAuthority("ADMIN").anyRequest().authenticated()
	            .antMatchers("/{username}").hasAuthority("ADMIN").anyRequest().authenticated()
	            .and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint()).and()
	            .apply(new JwtConfigurer(jwtTokenProvider));
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}
	
	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
	    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
	            "Unauthorized");
	}
	
	@Bean
	public UserDetailsService mongoUserDetails() {
	    return new CustomUserDetailsService();
	}
}
