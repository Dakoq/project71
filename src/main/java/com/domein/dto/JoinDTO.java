package com.domein.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {
	
    private String username;
    private String password;
    private String MemberName;
    private String phone;
    private String comments;
    private String pushOption;
}