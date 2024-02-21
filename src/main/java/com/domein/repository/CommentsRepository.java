package com.domein.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.domein.entity.CommentsEntity;

public interface CommentsRepository extends CrudRepository<CommentsEntity, Integer>{

	List<CommentsEntity> findByArticleId(String articleId);
	
}
