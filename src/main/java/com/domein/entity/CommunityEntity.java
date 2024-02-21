package com.domein.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class CommunityEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 1000)
    private String text;
    
    private String date;
    
    private String editDate;
    
    @Column(nullable = false)
    private String role;
    
    //조회수
    private int views;
    
    //추천수
    private int recommend;
    
    
	
	
	
	
	
	
	
	
}
