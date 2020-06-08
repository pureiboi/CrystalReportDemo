package com.boc.crystalreportdemo.service;

import com.boc.crystalreportdemo.domain.Account;

public interface AccountService {

    public Iterable<Account> getAllAccount();

    public Account getAccountById(String id);

    public Account saveAccount(Account account);

    public void deleteAccount(String id);
}
