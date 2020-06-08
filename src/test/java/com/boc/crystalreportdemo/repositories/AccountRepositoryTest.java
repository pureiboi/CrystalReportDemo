package com.boc.crystalreportdemo.repositories;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.boc.crystalreportdemo.domain.Account;


@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;
	
    @Test
    public void testSaveAccount(){
    	
        long countInit = accountRepository.count();

        
        Account account = new Account();
        
        account.setAccName("my account");
        

        
        accountRepository.save(account);
        
        long countFinal = accountRepository.count();
        assertThat(countFinal).isEqualTo(countInit+1);
    }
    

}
