package com.project.backend;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
	private static final Logger logger = LogManager.getLogger(BackendApplication.class);
	public static void main(String[] args) {

		logger.info("Starting Springboot application");
		SpringApplication.run(BackendApplication.class, args);
	}

}
