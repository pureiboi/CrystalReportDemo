package com.boc.crystalreportdemo.repositories;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.boc.crystalreportdemo.domain.Transaction;


@DataJpaTest
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;

    @Test
    public void testSaveTransaction(){
    	
        long countInit = transactionRepository.count();

        Transaction transaction = new Transaction();
        
        transaction.setPurpose("for saving");
        
        transactionRepository.save(transaction);
        
        long countFinal = transactionRepository.count();
        assertThat(countFinal).isEqualTo(countInit+1);
    }
    

}
