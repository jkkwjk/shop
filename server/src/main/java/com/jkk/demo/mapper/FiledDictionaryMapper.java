package com.jkk.demo.mapper;

import com.jkk.demo.model.dal.FiledDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiledDictionaryMapper extends JpaRepository<FiledDictionary, String> {
	List<FiledDictionary> findByCodeIsLike(String code);
}
