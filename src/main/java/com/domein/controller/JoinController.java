package com.domein.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.domein.dto.JoinDTO;
import com.domein.service.JoinService;

@Controller
@ResponseBody
public class JoinController {
    
    private final JoinService joinService;

    public JoinController(JoinService joinService) {
       
        this.joinService = joinService;
    }
    //중복검사 
    @PostMapping("/api/check_username")
    public boolean joinCheckEmail(@RequestBody JoinDTO joinDTO) {
    	return joinService.joinCheckUsername(joinDTO);
    }
    
    //회원가입
    @PostMapping("/api/join")
    public ResponseEntity<?> joinProcess(@RequestBody JoinDTO joinDTO) {
        return joinService.joinProcess(joinDTO);
    }
    
}