package com.demo.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.crystalreportdemo.domain.Branch;

public interface BranchRepository extends JpaRepository<Branch, String>{

}