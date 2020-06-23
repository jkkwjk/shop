package com.jkk.demo.mapper;

import com.jkk.demo.model.dal.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends JpaRepository<UserDO, Integer> {
	UserDO findByNickNameAndPwd(String nickName, String pwd);

	UserDO findByTelephoneAndPwd(String telephone, String pwd);
}
