package com.domein;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

@RestController
@SpringBootApplication
public class JisungMapleProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(JisungMapleProjectApplication.class, args);
	}
	
}
