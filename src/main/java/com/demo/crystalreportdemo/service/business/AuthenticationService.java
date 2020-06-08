package com.demo.crystalreportdemo.service.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.crystalreportdemo.domain.User;
import com.demo.crystalreportdemo.service.UserService;

@Service
public class AuthenticationService {

	@Autowired
	private UserService userservice;
	
	public boolean isUserExists(String userName) {
		User userObj = new User();
		userObj.setUserName(userName);
		return isAccountExists(userObj);
	}
	
	public boolean isAccountExists(User userObj) {
		return getUserAccount(userObj) !=null;
	}
	
	public User getUserAccount(String userName) {
		User userObj = new User();
		userObj.setUserName(userName);
		return getUserAccount(userObj);
	}
	
	public User getUserAccount(User userObj) {
		
		if(userObj != null && userObj.getUserName() != null) {
			userObj.setUserName(userObj.getUserName().toUpperCase());
		}
		
		return userservice.getUserByEntity(userObj);
	}
}
