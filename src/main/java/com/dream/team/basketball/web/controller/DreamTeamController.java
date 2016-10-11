package com.dream.team.basketball.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dream.team.basketball.entity.DreamTeam;
import com.dream.team.basketball.service.DreamTeamService;
import com.dream.team.basketball.web.dto.DreamTeamDTO;

@RestController
@RequestMapping("/api/dreamteam")
public class DreamTeamController {
	
	@Autowired 
	DreamTeamService dts;
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DreamTeamDTO>> getDreamTeams(){
		List<DreamTeam> dTeams = dts.findAll();
		List<DreamTeamDTO> dreamTeams = new ArrayList<DreamTeamDTO>();
		for(DreamTeam dreamTeam : dTeams)
			dreamTeams.add(new DreamTeamDTO(dreamTeam));
		
		return new ResponseEntity<List<DreamTeamDTO>>(dreamTeams, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DreamTeamDTO> getDreamTeam(@PathVariable Integer id){
		DreamTeam dreamTeam = dts.findOne(id);
		if(dreamTeam == null)
			return new ResponseEntity<DreamTeamDTO>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<DreamTeamDTO>(new DreamTeamDTO(dreamTeam), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<DreamTeamDTO> saveDreamTeam(@RequestBody DreamTeamDTO dreamTeamDTO){
		DreamTeam dreamTeam = new DreamTeam();
		dreamTeam.setName(dreamTeamDTO.getName());
		dreamTeam.setSuccessIndex(dreamTeamDTO.getSuccessIndex());
		dreamTeam.setPlayers(dreamTeamDTO.getPlayers());
		dreamTeam = dts.save(dreamTeam);
		return new ResponseEntity<DreamTeamDTO>(new DreamTeamDTO(dreamTeam), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<DreamTeamDTO> updateDreamTeam(@RequestBody DreamTeamDTO dreamTeamDTO){
		DreamTeam dreamTeam = dts.findOne(dreamTeamDTO.getId());
		if(dreamTeam == null)
			return new ResponseEntity<DreamTeamDTO>(HttpStatus.NOT_FOUND);
		dreamTeam.setName(dreamTeamDTO.getName());
		dreamTeam.setSuccessIndex(dreamTeamDTO.getSuccessIndex());
		dreamTeam.setPlayers(dreamTeamDTO.getPlayers());
		dreamTeam = dts.save(dreamTeam);
		return new ResponseEntity<DreamTeamDTO>(new DreamTeamDTO(dreamTeam), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteDreamTeam(@PathVariable Integer id){
		DreamTeam dreamTeam = dts.findOne(id);
		if(dreamTeam == null)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		else{
			dts.remove(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	}
}
