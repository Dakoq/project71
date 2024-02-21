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
public class RecommendationEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@Column(nullable = false)
    private int postId;
	
	@Column(nullable = false)
    private String userId;

	@Column(nullable = false)
	private Integer recommendation;
	
	
}
