package com.domein.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentsDTO {

	private int id;
	private String articleId;
	private String username;
	private String text;
    private String date;

}
