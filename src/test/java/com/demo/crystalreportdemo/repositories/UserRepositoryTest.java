package com.demo.crystalreportdemo.repositories;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;

import com.demo.crystalreportdemo.domain.Account;
import com.demo.crystalreportdemo.domain.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
//	@Autowired
//	private UserService userService;

    @Test
    public void testSaveUser(){
    	
        long userCountInit = userRepository.count();

        User user = new User();
       
        user.setFullName("im new name");
        user.setUserName("userx1");
        userRepository.save(user);

        long userCount = userRepository.count();
        assertThat(userCount).isEqualTo(userCountInit + 1);

        
    }
    
    @Test
    public void testHoldingAccount() {
    	
        User user = new User();
        user.setUserName("user1");
               
        Optional<User> result = userRepository.findOne(Example.of(user));
        
        //user = userService.getUserByEntity(user);
        
        User userResult = null;
        
        if(result.isPresent())
        {
        	userResult = result.get();
        }
        
   
        assertThat(userResult).isNotNull();
        
        assertThat(userResult.getUserId()).isNotNull();
       
        Set<Account> accounts = userResult.getAccounts();
        
        assertThat(accounts).isNotNull();
        
        Account acc = new Account();
        User accUser = new User();
        accUser.setUserId("userId1");
        acc.setUser(accUser);
        
        List<Account> accResult= accountRepository.findAll(Example.of(acc));
        

        assertThat(accResult).isNotNull();
        
        assertThat(accounts.size()).isEqualTo(accResult.size());

    }
}
