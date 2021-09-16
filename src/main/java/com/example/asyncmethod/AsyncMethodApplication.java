package com.example.asyncmethod;

import com.LogColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class AsyncMethodApplication  implements CommandLineRunner {
    private static Logger LOG = LoggerFactory
            .getLogger(AsyncMethodApplication.class);



    @Autowired
    private GithubLookupService githubLookupService;

    public static void main(String[] args) {
        LOG.info("Start");
        LOG.info(LogColors.ANSI_RED + Thread.currentThread().getName() + LogColors.ANSI_RESET);
        SpringApplication.run(AsyncMethodApplication.class, args).close();
        LOG.info("End");
    }


    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");

        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }
    }
}
