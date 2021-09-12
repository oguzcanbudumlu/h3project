package com.h3project.server;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class H3Service {

    @Autowired
    Logger LOG;

    @Autowired
    H3Dao h3Dao;

    public String getGeojsonByQuery(String query) {
        return this.h3Dao.getGeojsonByQuery(query);
    }

    @Async
    public CompletableFuture<Object> testAsync(String query) throws InterruptedException, ExecutionException {
        LOG.info("testAsync start");
        LOG.info(Thread.currentThread().getName());
        String geojson = h3Dao.testAsync(query);
        Thread.sleep(3000L);
        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture(geojson);
        String s = completedFuture.get();
        countPoints(s, 10);
        LOG.info("testAsync end");
        return CompletableFuture.completedFuture(geojson);
    }

    JSONObject countPoints(String geojsonAsString, int res) {
        LOG.info(geojsonAsString);
        LOG.info(String.valueOf(res));
        return null;

    }




}
