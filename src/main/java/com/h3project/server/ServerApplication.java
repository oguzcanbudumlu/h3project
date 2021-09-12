package com.h3project.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {
	private static Logger LOG = LoggerFactory
			.getLogger(ServerApplication.class);

	public static void main(String[] args) {
		LOG.info("Server app is starting");
		SpringApplication.run(ServerApplication.class, args);
		LOG.info("Server app is ending");
	}

}
