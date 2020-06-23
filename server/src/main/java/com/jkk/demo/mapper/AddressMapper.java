package com.jkk.demo.mapper;

import com.jkk.demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
public interface AddressMapper extends JpaRepository<Address, Integer> {
	int deleteByIdAndUserId(Integer id, Integer userId);

	List<Address> findAllByUserIdOrderByDefaultStatusDesc(Integer userId);

	Address findByDefaultStatusAndUserId(@NotNull Boolean defaultStatus, Integer userId);

	int countByUserId(Integer userId);

	int countByUserIdAndDefaultStatus(Integer userId, @NotNull Boolean defaultStatus);

	Address findByIdAndUserId(Integer id, Integer userId);
}
