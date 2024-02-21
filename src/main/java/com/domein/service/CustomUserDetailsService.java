package com.domein.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.domein.dto.CustomUserDetails;
import com.domein.entity.UserEntity;
import com.domein.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String Username) throws UsernameNotFoundException {
				
		//DB에서 조회
        UserEntity userData = userRepository.findByUsername(Username);

//        if (userData == null) {
//            throw new UsernameNotFoundException("User not found with email: " + Username);
//        }
        if(userData != null) {
        	return new CustomUserDetails(userData);        	
        }
		return null;
    }
}