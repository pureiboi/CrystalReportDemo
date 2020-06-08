package com.demo.crystalreportdemo.service;

import com.demo.crystalreportdemo.domain.Branch;

public interface BranchService {

    public Iterable<Branch> getAllBranch();

    public Branch getBranchById(String id);

    public Branch saveBranch(Branch branch);

    public void deleteBranch(String id);
}
