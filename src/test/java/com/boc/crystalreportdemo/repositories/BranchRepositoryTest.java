package com.boc.crystalreportdemo.repositories;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.boc.crystalreportdemo.domain.Branch;

@DataJpaTest
public class BranchRepositoryTest {

	@Autowired
	private BranchRepository branchRepository;

    @Test
    public void testSaveBranch(){
    	
        long countInit = branchRepository.count();


        Branch branch = new Branch();
        
        branch.setBranchNo("7694");
        
        branchRepository.save(branch);
        
        long countFinal = branchRepository.count();
        assertThat(countFinal).isEqualTo(countInit+1);
    }
    

}
