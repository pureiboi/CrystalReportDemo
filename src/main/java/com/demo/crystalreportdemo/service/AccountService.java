package com.demo.crystalreportdemo.service;

import com.demo.crystalreportdemo.domain.Account;

public interface AccountService {

    public Iterable<Account> getAllAccount();

    public Account getAccountById(String id);

    public Account saveAccount(Account account);

    public void deleteAccount(String id);
}
