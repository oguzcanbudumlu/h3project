package com.example.asyncmethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CommandLineApp implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(CommandLineApp.class);

    @Autowired
    private GithubLookupService githubLookupService;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("CommandLineApp run method");
        CompletableFuture<User> user = githubLookupService.findUser("oguzcanbudumlu");
        CompletableFuture.allOf(user);
        System.out.println(user.get());
    }
}
