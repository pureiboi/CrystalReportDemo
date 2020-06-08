package com.demo.crystalreportdemo.service;

import com.demo.crystalreportdemo.domain.Transaction;

public interface TransactionService {

    public Iterable<Transaction> getAllTransaction();

    public Transaction getTransactionById(String id);

    public Transaction saveTransaction(Transaction transaction);

    public void deleteTransaction(String id);
}
