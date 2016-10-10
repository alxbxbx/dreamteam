package com.dream.team.basketball.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dream.team.basketball.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findUserByUsername(String username);

}
