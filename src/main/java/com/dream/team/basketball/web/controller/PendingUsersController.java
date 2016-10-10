package com.dream.team.basketball.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dream.team.basketball.entity.User;
import com.dream.team.basketball.service.UserService;
import com.dream.team.basketball.web.dto.UserDTO;

@RestController
@RequestMapping("api/pendingusers")
public class PendingUsersController {
	
	@Autowired
	UserService userService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getPending(){
		List<User> users = userService.usersForApprove();
		List<UserDTO> dtoUsers = new ArrayList<UserDTO>();
		for(User user : users){
			dtoUsers.add(new UserDTO(user.getId(), user.getUsername(),user.getName(), user.getLastName(), user.getRole(), user.getEmail(), user.isActive(), user.isApproved()));
		}
		return new ResponseEntity<List<UserDTO>>(dtoUsers, HttpStatus.OK);
	}
}
