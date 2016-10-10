package com.dream.team.basketball.web.dto;

import java.util.List;

import com.dream.team.basketball.entity.DreamTeam;

public class DreamTeamDTO {
	
	private Integer id;
	private String name;
	private Integer successIndex;
	private List<String> players;
	
	public DreamTeamDTO(){}
	
	public DreamTeamDTO(DreamTeam dreamTeam){
		this.id = dreamTeam.getId();
		this.name = dreamTeam.getName();
		this.successIndex = dreamTeam.getSuccessIndex();
		this.players = dreamTeam.getPlayers();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSuccessIndex() {
		return successIndex;
	}

	public void setSuccessIndex(Integer successIndex) {
		this.successIndex = successIndex;
	}

	public List<String> getPlayers() {
		return players;
	}

	public void setPlayers(List<String> players) {
		this.players = players;
	}
	
	
}
