package com.domein.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.domein.dto.MemberDTO;
import com.domein.entity.UserEntity;
import com.domein.service.MemberService;

@RestController
public class MemberController {
	
	private final MemberService memberService;
	
	public MemberController(MemberService memberService) {
		
		this.memberService = memberService;
	}

	// 내 정보 받기
	@PostMapping("/api/member")
    public UserEntity postMethodName(@RequestBody MemberDTO memberDTO) {
        UserEntity userEntity =  memberService.getMyInfo(memberDTO.getUsername());
        return userEntity;
    }
	
	// 내 정보 수정
	@PostMapping("/api/memberUpdate")
	public ResponseEntity<?> memberUpdate(@RequestBody MemberDTO memberDTO) {
		return memberService.memberUpdate(memberDTO);
	}
	
	@PostMapping("/api/memberDelete")
    public String deleteMember(@RequestParam("username") String username) {
        memberService.deleteUserByUsername(username);
        return "회원 탈퇴가 완료되었습니다.";
    }

    
	
	
}
