package com.domein.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.domein.dto.CommentsDTO;
import com.domein.entity.CommentsEntity;
import com.domein.repository.CommentsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentsService {

	private final CommentsRepository commentsRepository;
	
	public CommentsService(CommentsRepository commentsRepository) {
		this.commentsRepository = commentsRepository;
	}
	// 댓글 작성
	public ResponseEntity<?> createComments(CommentsDTO commentsDTO) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm");
		String formattedDate = currentDateTime.format(formatter);
		String date = formattedDate;
		
		CommentsEntity data = new CommentsEntity();
		
		data.setDate(date);
		data.setArticleId(commentsDTO.getArticleId());
		data.setText(commentsDTO.getText());
		data.setUsername(commentsDTO.getUsername());
		
		commentsRepository.save(data);
		return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body("댓글이 성공적으로 작성되었습니다.");
	}
	
	// 댓글 게시글 리스트
	public List<CommentsDTO> getComments(String articleId) {
		System.out.println(articleId);
	    // 데이터베이스에서 특정 게시글 ID에 해당하는 댓글 목록을 조회
	    List<CommentsEntity> commentsEntities = commentsRepository.findByArticleId(articleId);

	    // Entity 목록을 DTO 목록으로 변환
	    return commentsEntities.stream().map(this::convertToDto).collect(Collectors.toList());
	}

    
    // Entity를 DTO로 변환하는 메서드
    private CommentsDTO convertToDto(CommentsEntity entity) {
    	CommentsDTO dto = new CommentsDTO();
        dto.setId(entity.getId());
        dto.setArticleId(entity.getArticleId());
        dto.setUsername(entity.getUsername());
        dto.setText(entity.getText());
        dto.setDate(entity.getDate());
      
        return dto;
    }
    
    // 댓글 수정 
    public CommentsDTO updateComments(int id, CommentsDTO commentsDTO) {
        // Optional 객체에서 CommentsEntity를 추출합니다.
        Optional<CommentsEntity> optionalCommentsEntity = commentsRepository.findById(id);

        // Optional 객체가 값을 포함하고 있는지 확인합니다.
        if (optionalCommentsEntity.isPresent()) {
            CommentsEntity commentsEntity = optionalCommentsEntity.get();

            commentsEntity.setText(commentsDTO.getText());
            
            // 업데이트된 엔티티를 저장합니다.
            commentsEntity = commentsRepository.save(commentsEntity);

            return convertToDto(commentsEntity);
        } else {
            throw new EntityNotFoundException("Comment not found with id " + id);
        }
    }
    
    // 커뮤니티 게시글 삭제 
    public boolean deleteComments(int id) {
        if(!commentsRepository.existsById(id)) {
            // 게시글이 존재하지 않는 경우 false 반환
            return false;
        }
        // 게시글이 존재하는 경우 삭제 수행
        commentsRepository.deleteById(id);
        return true;
    }

    
}



























