package com.domein.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterDTO {

	private String email;
	private String password;
	private String fullName;
	private String phone;
	private String comments;
	private String pushNotifications;
}
