package com.demo.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.crystalreportdemo.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String>{

}