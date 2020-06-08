package com.boc.crystalreportdemo.service;

import com.boc.crystalreportdemo.domain.Transaction;

public interface TransactionService {

    public Iterable<Transaction> getAllTransaction();

    public Transaction getTransactionById(String id);

    public Transaction saveTransaction(Transaction transaction);

    public void deleteTransaction(String id);
}
