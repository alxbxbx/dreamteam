package com.dream.team.basketball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableAutoConfiguration
@EnableResourceServer
public class DreamTeamApp{
	
	public static void main(String args[]){
		SpringApplication.run(DreamTeamApp.class, args);
	}
	
}