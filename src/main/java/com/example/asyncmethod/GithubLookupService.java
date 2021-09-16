package com.example.asyncmethod;

import com.LogColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;


@Service
public class GithubLookupService {

    private final RestTemplate restTemplate;

    private static Logger LOG = LoggerFactory
            .getLogger(GithubLookupService.class);


    public GithubLookupService(RestTemplateBuilder restTemplateBuilder) {
        LOG.info("EXECUTING: GithubLookupService constructing");
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<User> findUser(String user) throws InterruptedException {
        LOG.info(LogColors.ANSI_RED + Thread.currentThread().getName() + LogColors.ANSI_RESET );
        String url = String.format("https://api.github.com/users/%s", user);
        User results = restTemplate.getForObject(url, User.class);
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(results);
    }
}
