package com.domein.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.domein.dto.RecommendationDTO;
import com.domein.service.RecommendationService;

@RestController
@RequestMapping("/api/posts")
public class RecommendationController {

	private final RecommendationService recommendationService;
	
	public RecommendationController(RecommendationService recommendationService) {
		this.recommendationService = recommendationService;
	}

	@PostMapping("/{postId}/recommend")
    public ResponseEntity<?> recommendPost(@PathVariable("postId") int postId, @RequestBody RecommendationDTO dto) {
        recommendationService.recommendPost(postId, dto.getUserId(), dto.getRecommendation());
        return ResponseEntity.ok().build();  
    }
	
	// 게시글에서 추천 상태 확인
    @GetMapping("/{postId}/recommendation-status")
    public ResponseEntity<?> getRecommendationStatus(@PathVariable("postId") int postId, @RequestParam("userId") String userId) {
    	System.out.println("하이하"+userId);
    	Integer recommendationStatus = recommendationService.getRecommendationStatus(postId, userId);
        return ResponseEntity.ok().body(recommendationStatus);
    }
    
    @GetMapping("/{postId}/total-recommendations")
    public ResponseEntity<?> getTotalRecommendations(@PathVariable("postId") int postId) {
        // 해당 게시물의 총 추천 수를 서비스로부터 가져옵니다.
        int totalRecommendations = recommendationService.getTotalRecommendations(postId);
        return ResponseEntity.ok().body(totalRecommendations);
    }
	
}
 
