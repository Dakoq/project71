package com.domein.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDTO {
	
	private Long id;
    private String username;
    private String password;
    private String role;
    private String MemberName;
    private String phone;
    private String comments;
    private String pushOption;
}