package com.dream.team.basketball.entity;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class DreamTeam {
	
	@Id
	@GeneratedValue
	private Integer id;
	private Integer successIndex;
	private String name;
	
	@ElementCollection(targetClass=String.class)
	private List<String> players;
	
	public DreamTeam(){}

	

	public DreamTeam(Integer id, Integer successIndex, String name, List<String> players) {
		super();
		this.id = id;
		this.successIndex = successIndex;
		this.name = name;
		this.players = players;
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
