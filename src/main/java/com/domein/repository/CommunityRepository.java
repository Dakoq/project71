package com.domein.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.domein.entity.CommunityEntity;

public interface CommunityRepository extends CrudRepository<CommunityEntity, Integer> {

	@Override
	List<CommunityEntity> findAll();

	
}
