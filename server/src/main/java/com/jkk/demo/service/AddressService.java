package com.jkk.demo.service;

import com.jkk.demo.model.Address;

import java.util.List;

public interface AddressService {
	String canAddAddress(Integer userId);

	Address addAddress(Address address, Integer userId);

	Boolean deleteAddress(Integer id, Integer userId);

	Boolean setDefaultStatusAddress(Integer id, Integer userId, Boolean status);

	List<Address> getAllAddress(Integer userId);
}
