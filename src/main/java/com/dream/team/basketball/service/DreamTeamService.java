package com.dream.team.basketball.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.team.basketball.entity.DreamTeam;
import com.dream.team.basketball.repository.DreamTeamRepository;

@Service
public class DreamTeamService {
	
	@Autowired
	DreamTeamRepository dtr;
	
	public DreamTeam findOne(Integer id){
		return dtr.findOne(id);
	}
	
	public List<DreamTeam> findAll(){
		return dtr.findAll();
	}
	
	public DreamTeam save(DreamTeam dreamTeam){
		return dtr.save(dreamTeam);
	}
	
	public void remove(Integer id){
		dtr.delete(id);
	}
	

}
