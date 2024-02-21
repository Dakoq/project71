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
public class CommentsEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(nullable = false)
    private String articleId;
	
	@Column(nullable = false)
    private String username;
      
    @Column(nullable = false, length = 1000)
    private String text;
    
    @Column(nullable = false)
    private String date;
    
    
}
