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
    
    @PostMapping("/api/check_username")
    public boolean joinCheckEmail(@RequestBody JoinDTO joinDTO) {
        System.out.println("joinCheckEmail post : " + joinDTO.getUsername());
    	return joinService.joinCheckUsername(joinDTO);
    }

    @PostMapping("/api/join")
    public ResponseEntity<?> joinProcess(@RequestBody JoinDTO joinDTO) {
        System.out.println("join post : " + joinDTO.getUsername());
        return joinService.joinProcess(joinDTO);
    }
    
}