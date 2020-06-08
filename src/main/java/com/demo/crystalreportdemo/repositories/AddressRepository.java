package com.demo.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.crystalreportdemo.domain.Address;

public interface AddressRepository extends JpaRepository<Address, String>{

}