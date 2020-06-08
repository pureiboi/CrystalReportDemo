package com.boc.crystalreportdemo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.boc.crystalreportdemo.domain.User;
import com.boc.crystalreportdemo.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Override
	public Iterable<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(String id) {
		return userRepository.getOne(id);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getUserByEntity(User user) {
		Optional<User> result = userRepository.findOne(Example.of(user));
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = new User();
		user.setUserName(username);
		return getUserByEntity(user);
	}
	
	

}
