package com.domein.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.domein.dto.MemberDTO;
import com.domein.entity.UserEntity;
import com.domein.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class MemberService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public MemberService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder ) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	// 내 정보 가져오기
	public UserEntity getMyInfo(String username) {
	    return userRepository.findByUsername(username);		
	}
	// 회원 수정
	public ResponseEntity<?> memberUpdate(MemberDTO memberDTO) {
	    Long userId = memberDTO.getId();
	    // Long 타입의 ID를 Integer로 변환합니다.
	    Integer userIdInt = userId.intValue();
		
		UserEntity userEntity = userRepository.findById(userIdInt).orElse(null);
		
		if (userEntity != null) {
	        userEntity.setPassword(bCryptPasswordEncoder.encode(memberDTO.getPassword()));
	        userEntity.setComments(memberDTO.getComments());
	        userEntity.setPhone(memberDTO.getPhone());
	        userEntity.setPushOption(memberDTO.getPushOption());
	        userEntity.setMemberName(memberDTO.getMemberName());
		
	        userRepository.save(userEntity);
	        return ResponseEntity.ok("회원 정보가 성공적으로 업데이트 되었습니다.");
		} else {
	
			return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body("해당 회원을 찾을 수 없습니다.");
		}	
	}
	// 회원 탈퇴
 	@Transactional
    public void deleteUserByUsername(String username) {
 		UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            userRepository.delete(userEntity);
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
    }
	
	//회원 탈퇴
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
