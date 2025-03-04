package com.iprody.lms.credentialservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CredentialserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CredentialserviceApplication.class, args);
	}

}
