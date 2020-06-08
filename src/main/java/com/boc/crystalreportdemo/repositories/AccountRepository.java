package com.boc.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boc.crystalreportdemo.domain.Account;

public interface AccountRepository extends JpaRepository<Account, String>{

}