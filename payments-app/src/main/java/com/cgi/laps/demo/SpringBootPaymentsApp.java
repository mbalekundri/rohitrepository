package com.cgi.laps.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot application for customer accounts create/update via REST API
 * @author balekundrim
 *
 */
@SpringBootApplication
public class SpringBootPaymentsApp {
	
	private static final Logger LOGGER = LogManager.getLogger(SpringBootPaymentsApp.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPaymentsApp.class, args);
		LOGGER.info("SpringBootPaymentsApp started");
	}

}
