package com.barath.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.RestController;

import com.barath.cyberark.vault.configuration.EnableVaultAutoConfiguration;

@SpringBootApplication
//@EnableVaultAutoConfiguration
@RestController
public class DemoApplication {
	
	@Autowired
	private ConfigurableEnvironment environment;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	

	
	
}

