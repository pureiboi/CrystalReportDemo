package com.boc.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boc.crystalreportdemo.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String>{

}