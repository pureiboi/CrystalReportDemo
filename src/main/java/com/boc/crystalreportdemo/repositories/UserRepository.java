package com.boc.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boc.crystalreportdemo.domain.User;

public interface UserRepository extends JpaRepository<User, String>{

}