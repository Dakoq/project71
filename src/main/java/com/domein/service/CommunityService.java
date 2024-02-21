package com.domein.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.domein.dto.CommunityDTO;
import com.domein.entity.CommunityEntity;
import com.domein.repository.CommunityRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommunityService {
	
	private final CommunityRepository communityRepository;
	private final StringRedisTemplate stringRedisTemplate;
	
	public CommunityService(CommunityRepository communityRepository, StringRedisTemplate stringRedisTemplate) {
	        this.communityRepository = communityRepository;
	        this.stringRedisTemplate = stringRedisTemplate;
	}
	
	// 커뮤니티 게시글 작성
	public ResponseEntity<?> createCommunityArticle(CommunityDTO communityDTO) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy.MM.dd");
		String formattedDate = currentDateTime.format(formatter);
		String date = formattedDate;
	  
	    CommunityEntity data = new CommunityEntity();
	    
	    data.setDate(date);
	    data.setRecommend(0);
	    data.setRole(communityDTO.getRole());
	    data.setTitle(communityDTO.getTitle());
	    data.setText(communityDTO.getText());
	    data.setUsername(communityDTO.getUsername());
	    
	    communityRepository.save(data);
	    
	    return ResponseEntity
	            .status(HttpStatus.CREATED)
	            .body("게시글이 성공적으로 작성되었습니다.");
	}

	// 커뮤니티 게시글 리스트
    public List<CommunityDTO> getCommunityArticles() {
        // 데이터베이스에서 게시글 목록을 조회
        List<CommunityEntity> communityEntities = communityRepository.findAll();

        // Entity 목록을 DTO 목록으로 변환
        return communityEntities.stream().map(entity -> convertToDto(entity)).collect(Collectors.toList());
    }

    // Entity를 DTO로 변환하는 메서드
    private CommunityDTO convertToDto(CommunityEntity entity) {
        CommunityDTO dto = new CommunityDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setTitle(entity.getTitle());
        dto.setText(entity.getText());
        dto.setDate(entity.getDate());
        dto.setRole(entity.getRole());
        dto.setViews(entity.getViews());
        dto.setRecommend(entity.getRecommend());
        return dto;
    }
    // 커뮤니티 게시글 가져오기, 조회수Redis                   
    @Transactional
    public CommunityDTO getCommunityArticle(int id, String userIp) {
        final String cacheKey = "articleView:" + userIp + ":" + id;
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        
        // Redis에서 마지막 조회 시간을 조회
        String lastViewed = valueOperations.get(cacheKey);
        if (lastViewed == null) {
            // 조회수 증가 로직
            CommunityEntity communityEntity = communityRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id));
            
            communityEntity.setViews(communityEntity.getViews() + 1);
            communityRepository.save(communityEntity); // 업데이트된 조회수를 저장
            
            // 현재 시간을 Redis에 저장 (예: 1시간 후 만료)
            valueOperations.set(cacheKey, "viewed", 1, TimeUnit.HOURS);
        }

        return convertToDto(communityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id)));
    }


    // 커뮤니티 게시글 수정
    public CommunityDTO updateCommunityArticle(int id, CommunityDTO communityDTO) {
        CommunityEntity communityEntity = communityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id));

        communityEntity.setUsername(communityDTO.getUsername());
        communityEntity.setTitle(communityDTO.getTitle());
        communityEntity.setText(communityDTO.getText());
        communityEntity.setRole(communityDTO.getRole());
        // 필요하다면 여기에 다른 필드들도 업데이트할 수 있습니다.

        communityEntity = communityRepository.save(communityEntity); // 업데이트된 엔티티 저장

        return convertToDto(communityEntity); // 저장된 엔티티를 DTO로 변환 후 반환
    }
    
    // 커뮤니티 게시글 삭제 
    public boolean deleteCommunityArticle(int id) {
        if(!communityRepository.existsById(id)) {
            // 게시글이 존재하지 않는 경우 false 반환
            return false;
        }
        // 게시글이 존재하는 경우 삭제 수행
        communityRepository.deleteById(id);
        return true;
    }


    

	
	
	
	
}
