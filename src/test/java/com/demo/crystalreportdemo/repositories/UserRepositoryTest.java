package com.demo.crystalreportdemo.repositories;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;

import com.demo.crystalreportdemo.domain.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
    @Test
    public void testSaveUser(){
    	
        long userCountInit = userRepository.count();

        User user = new User();
       
        user.setFullName("im new name");
        user.setUserName("user1");
        userRepository.save(user);

        long userCount = userRepository.count();
        assertThat(userCount).isEqualTo(userCountInit + 1);

        
    }
    
    @Test
    public void testHoldingAccount() {
    	
        User user = new User();
        user.setUserName("user2");
               
        Optional<User> result = userRepository.findOne(Example.of(user));
        
        //user = userService.getUserByEntity(user);
        
        User userResult = null;
        
        if(result.isPresent())
        {
        	userResult = result.get();
        }
        
        assertThat(userResult).isNull();;

        userRepository.save(user);
        
        result = userRepository.findOne(Example.of(user));
        
        if(result.isPresent())
        {
        	userResult = result.get();
        }
        
   
        assertThat(userResult).isNotNull();;

        assertThat(userResult.getUserId()).isNotNull();

    }
}
