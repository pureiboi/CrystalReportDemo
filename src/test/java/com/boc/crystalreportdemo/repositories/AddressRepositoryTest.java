package com.boc.crystalreportdemo.repositories;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.boc.crystalreportdemo.domain.Address;

@DataJpaTest
public class AddressRepositoryTest {

	@Autowired
	private AddressRepository addressRepository;

    @Test
    public void testSaveAddress(){
    	
        long countInit = addressRepository.count();

        Address address = new Address();
        
        address.setPostCode("765143");
        address.setAddr1("Changi Expo");
        
        addressRepository.save(address);
        
        long countFinal = addressRepository.count();
        assertThat(countFinal).isEqualTo(countInit + 1);
    }
    

}
