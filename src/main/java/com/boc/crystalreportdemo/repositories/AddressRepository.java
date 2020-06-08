package com.boc.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boc.crystalreportdemo.domain.Address;

public interface AddressRepository extends JpaRepository<Address, String>{

}