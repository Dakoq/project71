package com.domein.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommunityDTO {

	private int id;
    private String username;
    private String title;
    private String text;
    private String date;
    private String role;
    private int views;
    private int recommend;
	
}
