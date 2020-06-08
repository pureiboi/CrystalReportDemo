package com.boc.crystalreportdemo.service;

import com.boc.crystalreportdemo.domain.User;

public interface UserService {

    public Iterable<User> getAllUser();

    public User getUserById(String id);

    public User saveUser(User user);

    public void deleteUser(String id);
    
    public User getUserByEntity(User user);
    
    public User getUserByUsername(String username);
}
