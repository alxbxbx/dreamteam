package com.dream.team.basketball.web.dto;

import com.dream.team.basketball.entity.User;

public class UserDTO {
	
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String lastName;
	private String role;
	private String email;
	private boolean isActive;
	private boolean approved;
	
	public UserDTO(){}
	
	public UserDTO(User user){
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.lastName = user.getLastName();
		this.role = user.getRole();
		this.email = user.getEmail();
		this.isActive = user.isActive();
		this.approved = user.isApproved();
	}
	
	
	public UserDTO(Integer id, String username, String name, String lastName, String role, String email,
			boolean isActive, boolean approved) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.role = role;
		this.email = email;
		this.isActive = isActive;
		this.approved = approved;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
	@Override
	public String toString(){
		return ("Username: " + this.username+ " email: " + this.email + " isActive: " + this.isActive + " approved: " + this.approved);
	}
	
	

}
