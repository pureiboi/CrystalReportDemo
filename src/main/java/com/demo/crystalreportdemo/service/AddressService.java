package com.demo.crystalreportdemo.service;

import com.demo.crystalreportdemo.domain.Address;

public interface AddressService {

    public Iterable<Address> getAllAddress();

    public Address getAddressById(String id);

    public Address saveAddress(Address address);

    public void deleteAddress(String id);
}
