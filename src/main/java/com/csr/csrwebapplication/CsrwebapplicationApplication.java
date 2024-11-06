package com.csr.csrwebapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CsrwebapplicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsrwebapplicationApplication.class, args);
	}

}
