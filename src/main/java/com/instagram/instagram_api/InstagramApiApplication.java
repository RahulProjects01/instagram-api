package com.instagram.instagram_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.instagram.instagram_api")
public class InstagramApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagramApiApplication.class, args);
	}

}
