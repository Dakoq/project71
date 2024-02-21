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

import com.domein.dto.CommentsDTO;
import com.domein.entity.CommentsEntity;
import com.domein.service.CommentsService;

@RestController
public class CommentsController {

	private final CommentsService commentsService;
	
	public CommentsController(CommentsService commentsService) {
		this.commentsService = commentsService;
	}
	
	// 댓글 작성
	@PostMapping("/api/community/comments")
	public ResponseEntity<?> createComments(@RequestBody CommentsDTO commentsDTO) {
		
		return commentsService.createComments(commentsDTO);
	}
	
	// 댓글 리스트
	@GetMapping("/api/comments/{articleId}")
	public ResponseEntity<List<CommentsDTO>> getComments(@PathVariable("articleId") String articleId) {
        // 커뮤니티 게시글 리스트를 가져오는 서비스 메서드 호출
        List<CommentsDTO> comments = commentsService.getComments(articleId);
        // 가져온 게시글 리스트를 ResponseEntity 객체에 담아 반환
        return ResponseEntity.ok(comments);
    }
	
	// 댓글 수정
	@PutMapping("api/comments/{commentId}")
	public ResponseEntity<?> updateComments(@PathVariable("commentId") int id, @RequestBody CommentsDTO commentsDTO) {
        // 서비스 계층을 통해 게시글 수정 로직을 수행하고 결과를 반환합니다.
		CommentsDTO updatedComments = commentsService.updateComments(id, commentsDTO);
        if(updatedComments == null) {
            // 업데이트 실패 응답 처리
            return ResponseEntity.notFound().build();
        }
        // 업데이트 성공 응답 처리
        return ResponseEntity.ok(updatedComments);
    }
	// 댓글 삭제
	@DeleteMapping("/api/comments/{commentId}")
	public ResponseEntity<?> deleteComments(@PathVariable("commentId") int id) {
		// 서비스 계층을 통해 게시글 삭제 로직을 수행
        boolean isDeleted = commentsService.deleteComments(id);

        if(isDeleted) {
            // 삭제에 성공했다면, HTTP 상태 코드 200(OK)를 반환
            return ResponseEntity.ok().build();
        } else {
            // 삭제에 실패했다면, HTTP 상태 코드 404(Not Found)를 반환
            return ResponseEntity.notFound().build();
        }
    }
	
}

























