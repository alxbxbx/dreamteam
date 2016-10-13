package com.dream.team.basketball.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dream.team.basketball.entity.User;
import com.dream.team.basketball.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	public User findOne(Integer id){
		User user = userRepository.findOne(id);
		if(user.isActive())
			return user;
		else
			return null;
	}
	
	//Return just active users
	public List<User> findAll(){
		List<User> users = userRepository.findAll();
		List<User> activeUsers = new ArrayList<User>();
		for(User user : users){
			if(user.isActive() && user.isApproved()){
				activeUsers.add(user);
			}
				
		}
		return activeUsers;
	}
	
	//Return deleted users (active status = 0)
	public List<User> inactiveUsers(){
		List<User> users = userRepository.findAll();
		List<User> activeUsers = new ArrayList<User>();
		for(User user : users){
			if(!user.isActive()){
				activeUsers.add(user);
			}
				
		}
		return activeUsers;
	}
	
	//Return users waiting to be approved
	public List<User> usersForApprove(){
		List<User> users = userRepository.findAll();
		List<User> activeUsers = new ArrayList<User>();
		for(User user : users){
			if(!user.isApproved())
				activeUsers.add(user);
		}
		return activeUsers;
	}
	
	
	
	public User save(User user){
		return userRepository.save(user);
	}
	
	public User findByUsername(String username){
		return userRepository.findUserByUsername(username);
	}
	
	public boolean delete(Integer id){
		User user = userRepository.findOne(id);
		if(user == null)
			return false;
		user.setActive(false);
		userRepository.save(user);
		return true;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findUserByUsername(username);
		if(user.isActive() && user.isApproved())
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getGrantedAuthorities(user));
		else 
			return null;
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        return authorities;
    }
	

}
