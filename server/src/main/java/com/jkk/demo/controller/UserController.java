package com.jkk.demo.controller;

import com.jkk.demo.aspect.ValidParam;
import com.jkk.demo.constant.SessionFiled;
import com.jkk.demo.model.User;
import com.jkk.demo.model.dal.UserDO;
import com.jkk.demo.service.UserService;
import com.jkk.demo.util.RestfulRes;
import com.jkk.demo.validator.CheckEmail;
import com.jkk.demo.validator.CheckPwd;
import com.jkk.demo.validator.CheckTelephone;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("login")
	public RestfulRes login(User user, HttpSession session){
		UserDO userDO = user.convert();
		userDO = userService.login(userDO);
		if (userDO != null){
			session.setAttribute(SessionFiled.USER_DO, userDO);
			return RestfulRes.success();
		}else {
			return RestfulRes.fail("账号或密码错误");
		}
	}

	@GetMapping("is_login")
	public RestfulRes isLogin(HttpSession session){
		if (session.getAttribute(SessionFiled.USER_DO) == null){
			return RestfulRes.fail();
		}else {
			return RestfulRes.success();
		}
	}

	@PostMapping("register")
	public RestfulRes register(@Valid User user, HttpSession session){
		UserDO userDO = user.convert();

		userService.register(userDO);
		session.setAttribute(SessionFiled.USER_DO, userDO);
		return RestfulRes.success();
	}

	@PostMapping("get_info")
	public RestfulRes<User> getInfo(@SessionAttribute(SessionFiled.USER_DO) UserDO userDO){
		return RestfulRes.success(userService.getInfo(userDO).convert());
	}


	@ValidParam
	@PostMapping("modify")
	public RestfulRes modify(HttpSession session, @SessionAttribute(SessionFiled.USER_DO) UserDO userDO,
	                         @RequestParam(required = false) @Length(min = 4, max = 13, message = "昵称要在4-13位") String nickName,
	                         @RequestParam(required = false) @CheckTelephone String telephone,
	                         @RequestParam(required = false) @CheckEmail String email,
	                         @RequestParam(required = false) Boolean sex,
	                         @RequestParam(required = false) @CheckPwd String pwd){
		UserDO newUserDO = new UserDO();
		BeanUtils.copyProperties(userDO, newUserDO);

		if (nickName != null){
			newUserDO.setNickName(nickName);
		}

		if (telephone != null){
			newUserDO.setTelephone(telephone);
		}

		if (email != null){
			newUserDO.setEmail(email);
		}

		if (sex != null){
			newUserDO.setSex(sex);
		}

		if (pwd != null){
			newUserDO.setPwd(pwd);
		}

		userDO = userService.modify(newUserDO);
		session.setAttribute(SessionFiled.USER_DO, userDO);
		return RestfulRes.success();
	}
}
