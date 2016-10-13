package com.dream.team.basketball.security;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dream.team.basketball.service.UserService;
import com.dream.team.basketball.web.controller.ExportController;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private final static Logger LOGGER = Logger.getLogger(ExportController.class.getName());
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	UserService userService;
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {		
		auth.authenticationProvider(authProvider());
                
                
    }
    
    @Override
    protected void configure(HttpSecurity http) {
     try {
		http
		    .authorizeRequests()
		    	.antMatchers(HttpMethod.OPTIONS, "/").permitAll()
		    	.antMatchers("/oauth/**").permitAll()
		    	.antMatchers("/api/users/register").permitAll()
		    	.antMatchers("/api/**").authenticated()
		        //.and().httpBasic()
		        .and().csrf().disable();
	} catch (Exception e) {
		LOGGER.info(e.getMessage());
	}
    }
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
