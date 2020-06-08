package com.boc.crystalreportdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.crystalreportdemo.domain.Branch;
import com.boc.crystalreportdemo.repositories.BranchRepository;

@Service
public class BranchServiceImpl implements BranchService{

	@Autowired
	private BranchRepository branchRepository;

	@Override
	public Iterable<Branch> getAllBranch() {
		return branchRepository.findAll();
	}

	@Override
	public Branch getBranchById(String id) {
		return branchRepository.getOne(id);
	}

	@Override
	public Branch saveBranch(Branch Branch) {
		return branchRepository.save(Branch);
	}

	@Override
	public void deleteBranch(String id) {
		branchRepository.deleteById(id);
	}

}
