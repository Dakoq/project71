package com.domein.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.domein.entity.RecommendationEntity;

public interface RecommendationRepository extends JpaRepository<RecommendationEntity, Integer> {
	RecommendationEntity findByPostIdAndUserId(int postId, String userId);

	List<RecommendationEntity> findByPostId(int postId);
}