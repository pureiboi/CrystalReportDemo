package com.boc.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boc.crystalreportdemo.domain.Branch;

public interface BranchRepository extends JpaRepository<Branch, String>{

}