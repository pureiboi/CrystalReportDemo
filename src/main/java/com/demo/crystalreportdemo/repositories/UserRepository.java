package com.demo.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.crystalreportdemo.domain.User;

public interface UserRepository extends JpaRepository<User, String>{

}