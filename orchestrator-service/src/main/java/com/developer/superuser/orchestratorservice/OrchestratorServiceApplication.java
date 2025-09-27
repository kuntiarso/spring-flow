package com.developer.superuser.orchestratorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.config.EnableIntegration;

@SpringBootApplication
@EnableIntegration
public class OrchestratorServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrchestratorServiceApplication.class, args);
	}
}