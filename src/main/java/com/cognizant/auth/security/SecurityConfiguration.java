package com.cognizant.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cognizant.auth.filter.JwtFilter;
import com.cognizant.auth.services.CustomUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	 @Autowired
	 private CustomUserDetailsService customDetailsService;
	 
	 @Autowired
	 private JwtFilter jwtFilter;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		 auth.userDetailsService(this.customDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	   
		  http
		      .cors().disable()
		      .csrf().disable()
		      .authorizeRequests()
		      .antMatchers("/login" , "/usernameExist/**" , "/validToken").permitAll()
		      .anyRequest().authenticated()
		      .and()
		      .sessionManagement()
		      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		  
		   http.addFilterBefore(this.jwtFilter , UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		 web
         .ignoring()
         .antMatchers("/h2-console/**");
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	

}
