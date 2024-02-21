package com.domein.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationDTO {
	private int id;
    private int postId;
    private String userId;
    private Integer recommendation;
}
