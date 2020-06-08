package com.demo.crystalreportdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.crystalreportdemo.domain.Transaction;
import com.demo.crystalreportdemo.repositories.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Iterable<Transaction> getAllTransaction() {
		return transactionRepository.findAll();
	}

	@Override
	public Transaction getTransactionById(String id) {
		return transactionRepository.getOne(id);
	}

	@Override
	public Transaction saveTransaction(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public void deleteTransaction(String id) {
		transactionRepository.deleteById(id);
	}


}
