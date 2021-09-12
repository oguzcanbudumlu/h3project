package com.h3project.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class H3CommandLineApp implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(H3CommandLineApp.class);

    @Autowired
    private H3Service h3Service;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("H3CommandLineApp run method is starting");

        long start = System.currentTimeMillis();
        CompletableFuture<Object> geojson = h3Service.testAsync("select * from quakes_tr");
        CompletableFuture.allOf(geojson);
        long end = System.currentTimeMillis();

        LOG.info("Elapsed time: " + (end-start)/1000.0 + " s");
        LOG.info("--> " + geojson.get());

    }
}
