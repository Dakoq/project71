package com.domein.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.domein.dto.CommunityDTO;
import com.domein.service.CommunityService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CommunityController {

	private final CommunityService communityService;
	
	public CommunityController(CommunityService communityService) {
		
		this.communityService = communityService;
	}
	
	// 커뮤니티 게시글 작성
	@PostMapping("/api/articles")
	public ResponseEntity<?> createCommunityArticle(@RequestBody CommunityDTO communityDTO) {
		
		
		return communityService.createCommunityArticle(communityDTO);
	}
	
	// 커뮤니티 게시글 리스트
    @GetMapping("/api/posts")
    public ResponseEntity<List<CommunityDTO>> getCommunityArticles() {
        // 커뮤니티 게시글 리스트를 가져오는 서비스 메서드 호출
        List<CommunityDTO> communityArticles = communityService.getCommunityArticles();
        // 가져온 게시글 리스트를 ResponseEntity 객체에 담아 반환
        return ResponseEntity.ok(communityArticles);
    }
    
    // 커뮤니티 게시글 상세 페이지
    @GetMapping("/api/community/posts/{id}")
    public ResponseEntity<CommunityDTO> getCommunityArticle(@PathVariable("id") int id, HttpServletRequest request) {
        // 클라이언트의 IP 주소를 얻습니다.
        String clientIp = request.getRemoteAddr();
        // ID와 IP를 기반으로 특정 커뮤니티 게시글을 가져오고 조회수를 증가시키는 서비스 메서드를 호출합니다.
        CommunityDTO communityArticle = communityService.getCommunityArticle(id, clientIp);
        // 가져온 게시글을 ResponseEntity 객체에 담아 반환합니다.
        return ResponseEntity.ok(communityArticle);
    }
    
    // 커뮤니티 게시글 수정
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<?> updateCommunityArticle(@PathVariable("id") int id, @RequestBody CommunityDTO communityDTO) {
        // 서비스 계층을 통해 게시글 수정 로직을 수행하고 결과를 반환합니다.
        CommunityDTO updatedArticle = communityService.updateCommunityArticle(id, communityDTO);
        if(updatedArticle == null) {
            // 업데이트 실패 응답 처리
            return ResponseEntity.notFound().build();
        }
        // 업데이트 성공 응답 처리
        return ResponseEntity.ok(updatedArticle);
    }
    
    // 커뮤니티 게시글 삭제
    @DeleteMapping("/api/community/posts/{id}")
    public ResponseEntity<?> deleteCommunityArticle(@PathVariable("id") int id) {
        // 서비스 계층을 통해 게시글 삭제 로직을 수행
        boolean isDeleted = communityService.deleteCommunityArticle(id);

        if(isDeleted) {
            // 삭제에 성공했다면, HTTP 상태 코드 200(OK)를 반환
            return ResponseEntity.ok().build();
        } else {
            // 삭제에 실패했다면, HTTP 상태 코드 404(Not Found)를 반환
            return ResponseEntity.notFound().build();
        }
    }
}


















