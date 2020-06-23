package com.jkk.demo.service.impl;

import com.jkk.demo.mapper.AddressMapper;
import com.jkk.demo.model.Address;
import com.jkk.demo.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
	private final AddressMapper addressMapper;

	public AddressServiceImpl(AddressMapper addressMapper) {
		this.addressMapper = addressMapper;
	}

	@Override
	public String canAddAddress(Integer userId) {
		if (addressMapper.countByUserId(userId) >= 20)
			return "最多只能添加20条数据";

		return null;
	}

	@Transactional
	@Override
	public Address addAddress(Address address, Integer userId) {
		if (address.getDefaultStatus()){
			setAllAddressNotDefault(userId);
		}
		address.setUserId(userId);
		return addressMapper.save(address);
	}

	@Transactional
	@Override
	public Boolean deleteAddress(Integer id, Integer userId) {
		return addressMapper.deleteByIdAndUserId(id, userId) == 1;
	}

	@Transactional
	@Override
	public Boolean setDefaultStatusAddress(Integer id, Integer userId, Boolean status) {
		// 安全性校验
		Optional<Address> addressOptional = addressMapper.findById(id);
		if (!addressOptional.isPresent() || !addressOptional.get().getUserId().equals(userId)){
			return false;
		}

		this.setAllAddressNotDefault(userId);


		Address address = addressMapper.findById(id).orElse(null);
		Assert.notNull(address, "地址没有找到" + Arrays.toString(Thread.currentThread().getStackTrace())); //用log代替
		address.setDefaultStatus(status);
		return addressMapper.save(address).getDefaultStatus() == status;
	}

	@Override
	public List<Address> getAllAddress(Integer userId) {
		return addressMapper.findAllByUserIdOrderByDefaultStatusDesc(userId);
	}

	private void setAllAddressNotDefault(Integer userId){
		Address defaultAddress = addressMapper.findByDefaultStatusAndUserId(true, userId);
		if (defaultAddress != null){
			defaultAddress.setDefaultStatus(false);
			addressMapper.save(defaultAddress);
		}
	}
}
