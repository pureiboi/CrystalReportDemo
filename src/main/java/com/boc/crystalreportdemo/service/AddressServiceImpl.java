package com.boc.crystalreportdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boc.crystalreportdemo.domain.Address;
import com.boc.crystalreportdemo.repositories.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Iterable<Address> getAllAddress() {
		return addressRepository.findAll();
	}

	@Override
	public Address getAddressById(String id) {
		return addressRepository.getOne(id);
	}

	@Override
	public Address saveAddress(Address address) {
		return addressRepository.save(address);
	}

	@Override
	public void deleteAddress(String id) {
		addressRepository.deleteById(id);
	}


}
