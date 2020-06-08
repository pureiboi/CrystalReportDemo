package com.demo.crystalreportdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.crystalreportdemo.domain.Account;
import com.demo.crystalreportdemo.repositories.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Iterable<Account> getAllAccount() {
		return accountRepository.findAll();
	}

	@Override
	public Account getAccountById(String id) {
		return accountRepository.getOne(id);
	}

	@Override
	public Account saveAccount(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public void deleteAccount(String id) {
		accountRepository.deleteById(id);
	}


}
