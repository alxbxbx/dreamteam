package com.dream.team.basketball.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dream.team.basketball.entity.User;
import com.dream.team.basketball.service.UserService;
import com.dream.team.basketball.web.dto.UserDTO;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getAll(){
		List<User> users = userService.findAll();
		List<UserDTO> dtoUsers = new ArrayList<UserDTO>();
		for(User user : users){
			dtoUsers.add(new UserDTO(user.getId(), user.getUsername(),user.getName(), user.getLastName(), user.getRole(), user.getEmail(), user.isActive(), user.isApproved()));
		}
		return new ResponseEntity<List<UserDTO>>(dtoUsers, HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<UserDTO> findOne(@PathVariable Integer id){
		User user = userService.findOne(id);
		if(user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<UserDTO>(new UserDTO(user.getId(), user.getUsername(),user.getName(), user.getLastName(), user.getRole(), user.getEmail(), user.isActive(), user.isApproved()), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<UserDTO> save(@RequestBody UserDTO userDTO){
		User user;
		try{
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
			user = new User(userDTO.getUsername(), hashedPassword, userDTO.getName(), userDTO.getLastName(), userDTO.getRole(), userDTO.getEmail(), userDTO.isActive(), userDTO.isApproved());
			user = userService.save(user);
			userDTO = new UserDTO(user);
		}catch(NullPointerException e){
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO){	
		User user;
		try{
			if((userService.findByUsername(userDTO.getUsername())) != null)
				return new ResponseEntity<UserDTO>(HttpStatus.NOT_ACCEPTABLE);
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
			user = new User(userDTO.getUsername(), hashedPassword, userDTO.getName(), userDTO.getLastName(), "USER", userDTO.getEmail(), true, false);
			user = userService.save(user);
			userDTO = new UserDTO(user);
		}catch(NullPointerException e){
			return new ResponseEntity<UserDTO>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO){
		User user = userService.findOne(userDTO.getId());
		if(user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		user.setName(userDTO.getName());
		user.setLastName(userDTO.getLastName());
		user.setUsername(userDTO.getUsername());
		user.setRole(userDTO.getRole());
		user.setEmail(userDTO.getEmail());
		user.setActive(userDTO.isActive());
		user.setApproved(userDTO.isApproved());
		
		userDTO = new UserDTO(userService.save(user));
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/changepassword", method = RequestMethod.PUT)
	public ResponseEntity<UserDTO> updatePassword(@RequestBody UserDTO userDTO){
		User user = userService.findOne(userDTO.getId());
		if(user == null)
			return new ResponseEntity<UserDTO>(HttpStatus.NOT_FOUND);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
		user.setPassword(hashedPassword);
		userDTO = new UserDTO(userService.save(user));
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		if(userService.delete(id))
			return new ResponseEntity<Void>(HttpStatus.OK);
		else
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	

}
