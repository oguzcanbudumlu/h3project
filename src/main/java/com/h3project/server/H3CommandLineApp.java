package com.h3project.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class H3CommandLineApp implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(H3CommandLineApp.class);

    @Autowired
    private H3Service h3Service;

    @Override
    public void run(String... args) throws Exception {
        LOG.info("H3CommandLineApp run method is starting");

        createLayerInDatabase();
        calculateLayer();
        getLayerFromDatabase("testing");
    }

    public void createLayerInDatabase() {
        h3Service.createLayer("testing", "select * from quakes_tr", 5);
    }

    public void getLayerFromDatabase(String tableName) {
        String layer = h3Service.getLayerFromDatabase(tableName);
        LOG.info(layer);
    }

    public void calculateLayer() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        CompletableFuture<Object> layer = h3Service.calculateLayer(10, "select * from quakes_tr");

        CompletableFuture<Object> test = h3Service.testAnotherThread();
        CompletableFuture.allOf(layer, test);
        long end = System.currentTimeMillis();

        LOG.info("Elapsed time: " + (end-start)/1000.0 + " s");
        LOG.info("--> " + layer.get());
    }
}
