package com.dream.team.basketball.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/players")
public class PlayersController {
	
	final static Logger LOGGER = LoggerFactory.getLogger(ExportController.class);
	
	//Method communcates with external API and retrieves all players
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> get(){
		try{
			URL players = new URL("http://localhost:8888/api/players");
	        URLConnection yc = players.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	        String inputLine;
	        inputLine = in.readLine();
	        in.close();
	        
	        return new ResponseEntity<String>(inputLine, HttpStatus.OK);
		}catch (Exception e){
			LOGGER.error(e.getMessage());
		}
		
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		
	}

}
