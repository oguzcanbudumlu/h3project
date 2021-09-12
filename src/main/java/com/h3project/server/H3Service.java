package com.h3project.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class H3Service {

    @Autowired
    H3Dao h3Dao;

    public String getGeojsonByQuery(String query) {
        return this.h3Dao.getGeojsonByQuery(query);
    }

    @Async
    public CompletableFuture<Object> asyncTest(String query) throws InterruptedException {
        String geojson = h3Dao.asyncTest(query);
        Thread.sleep(3000L);
        return CompletableFuture.completedFuture(geojson);
    }

}
