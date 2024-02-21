package com.domein.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.domein.entity.CommunityEntity;
import com.domein.entity.RecommendationEntity;
import com.domein.repository.CommunityRepository;
import com.domein.repository.RecommendationRepository;

import jakarta.transaction.Transactional;

@Service
public class RecommendationService {

	private final RecommendationRepository recommendationRepository;
	private final CommunityRepository communityRepository;
	
	public RecommendationService(RecommendationRepository recommendationRepository, CommunityRepository communityRepository) {
		this.recommendationRepository = recommendationRepository;
		this.communityRepository = communityRepository;
	}


	// 추천수 유저 확인 및 추천수 DB 반영
	@Transactional
	public void recommendPost(int postId, String userId, Integer recommendation) {
	    // 사용자의 이전 추천을 찾습니다.
	    RecommendationEntity existingRecommendation = recommendationRepository.findByPostIdAndUserId(postId, userId);

	    // communityEntity의 추천수를 가져옵니다.
	    CommunityEntity communityPost = communityRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));

	    int currentRecommendation = communityPost.getRecommend();

	    if (existingRecommendation != null) {
	        // 기존 추천 상태에 따라 현재 추천수를 조정합니다.
	        if (existingRecommendation.getRecommendation() == 1 && recommendation != 1) {
	            // 추천을 취소하거나 비추천으로 변경
	            currentRecommendation -= 1;
	        } else if (existingRecommendation.getRecommendation() == -1 && recommendation != -1) {
	            // 비추천을 취소하거나 추천으로 변경
	            currentRecommendation += 1;
	        }
	        // 기존 추천 상태를 새 상태로 업데이트
	        existingRecommendation.setRecommendation(recommendation);
	    } else {
	        // 새로운 추천을 생성하여 저장합니다.
	        RecommendationEntity newRecommendation = new RecommendationEntity();
	        newRecommendation.setPostId(postId);
	        newRecommendation.setUserId(userId);
	        newRecommendation.setRecommendation(recommendation);
	        
	        recommendationRepository.save(newRecommendation);
	    }

	    // 새로운 추천 상태에 따라 추천수를 업데이트합니다.
	    if (recommendation == 1) {
	        currentRecommendation += 1;
	    } else if (recommendation == -1) {
	        currentRecommendation -= 1;
	    }

	    // 변경된 추천수를 저장합니다.
	    communityPost.setRecommend(currentRecommendation);
	    communityRepository.save(communityPost);
	}



	 public Integer getRecommendationStatus(int postId, String userId) {
		 System.out.println("어떻게 가지고 오니?" + userId);
		 System.out.println("어떻게 가지고 오니?" + postId);

	        RecommendationEntity recommendationEntity = recommendationRepository.findByPostIdAndUserId(postId, userId);
	        // 추천 상태가 존재하면 그 상태를 반환하고, 그렇지 않다면 0을 반환합니다.
	        return recommendationEntity != null ? recommendationEntity.getRecommendation() : 0;
	    }


	 public int getTotalRecommendations(int postId) {
		    // 특정 게시물에 대한 모든 추천/비추천 합산
		    List<RecommendationEntity> recommendations = recommendationRepository.findByPostId(postId);
		    
		    // 추천 수 계산: 추천(+1)은 더하고, 비추천(-1)은 뺀다.
		    int totalRecommendations = 0;
		    for (RecommendationEntity recommendation : recommendations) {
		        totalRecommendations += recommendation.getRecommendation();
		    }
		    
		    return totalRecommendations;
		}

	
	
	
	
	

}
