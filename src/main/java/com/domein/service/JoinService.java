package com.domein.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.domein.dto.JoinDTO;
import com.domein.entity.UserEntity;
import com.domein.jwt.LoginFilter;
import com.domein.repository.UserRepository;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public Boolean joinCheckUsername(JoinDTO joinDTO) {
    	String username = joinDTO.getUsername();
    	Boolean isExist = userRepository.existsByUsername(username);
    	System.out.println(isExist);
    	return isExist;
    }
    
    public ResponseEntity<?> joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        if (userRepository.existsByUsername(username)) {
        	
            return ResponseEntity
            		.status(HttpStatus.CONFLICT)
            		.body("이미 사용 중인 이메일입니다. 다시 중복체크 하세요.");
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_USER");
        data.setComments(joinDTO.getComments());
        data.setPhone(joinDTO.getPhone());
        data.setPushOption(joinDTO.getPushOption());
        data.setMemberName(joinDTO.getMemberName());

        userRepository.save(data);
        
        return ResponseEntity
        		.status(HttpStatus.CREATED)
        		.body("회원가입이 성공적으로 완료되었습니다.");
    }
    
 
    
    
}



















