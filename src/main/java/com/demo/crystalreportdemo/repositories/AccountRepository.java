package com.demo.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.crystalreportdemo.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String>{

}