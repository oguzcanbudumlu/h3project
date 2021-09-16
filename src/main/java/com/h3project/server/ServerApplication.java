package com.h3project.server;

import com.LogColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
public class ServerApplication {
	private static Logger LOG = LoggerFactory
			.getLogger(ServerApplication.class);

	public static void main(String[] args) {
		LOG.info("Server app is starting");
		LOG.info(LogColors.ANSI_RED + Thread.currentThread().getName() + LogColors.ANSI_RESET);
		SpringApplication.run(ServerApplication.class, args);
		LOG.info("Server app is ending");
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("Thread");
		executor.initialize();
		return executor;
	}

}
