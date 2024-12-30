package com.ibra.ecommercePractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//@SpringBootApplication(scanBasePackages = "com.ibra.ecommercePractice")

@SpringBootApplication
@EnableCaching
public class EcommercePracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommercePracticeApplication.class, args);
	}
}
