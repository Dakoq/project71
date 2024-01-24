package com.domein.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.domein.entity.RegisterDTO;

@RestController
public class MemberController {

	@PostMapping("/api/register")
	public void register(@RequestBody RegisterDTO registerDTO) {

		System.out.println("/api/register 통신");
		System.out.println("---- 가져온 값 ----\n"+registerDTO.getEmail());
		System.out.println(registerDTO.getPassword());
		System.out.println(registerDTO.getComments());
		System.out.println(registerDTO.getFullName());
		System.out.println(registerDTO.getPhone());
		System.out.println(registerDTO.getPushNotifications());

		
				
				
				
				
		
	}
}
