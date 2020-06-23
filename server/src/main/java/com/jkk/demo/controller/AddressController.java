package com.jkk.demo.controller;

import com.jkk.demo.constant.SessionFiled;
import com.jkk.demo.model.Address;
import com.jkk.demo.model.dal.UserDO;
import com.jkk.demo.service.AddressService;
import com.jkk.demo.util.RestfulRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("address")
public class AddressController {
	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}

	@GetMapping
	public RestfulRes<List<Address>> getAllAddress(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO){
		return RestfulRes.success(addressService.getAllAddress(userDO.getId()));
	}

	@PostMapping
	public RestfulRes<Address> addAddress(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO,@Valid Address address){
		String msg = addressService.canAddAddress(userDO.getId());
		if (msg == null){
			return RestfulRes.success(addressService.addAddress(address, userDO.getId()));
		}else {
			return RestfulRes.fail(msg);
		}
	}

	@DeleteMapping
	public RestfulRes deleteAddress(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO, Integer id){
		if (addressService.deleteAddress(id, userDO.getId())){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("删除失败");
		}
	}

	@PutMapping
	public RestfulRes modifyAddress(@SessionAttribute(SessionFiled.USER_DO)UserDO userDO, Integer id, Boolean status){
		if (addressService.setDefaultStatusAddress(id, userDO.getId(), status)){
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("更新失败");
		}
	}
}
