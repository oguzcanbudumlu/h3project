package com.h3project.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
public class H3Controller {
    @Autowired
    H3Service h3Service;

    @PostMapping("/layers")
    String newLayer(@RequestBody H3LayerRequestBody h3LayerRequestBody) throws ExecutionException, InterruptedException {
        h3Service.createLayer(
                h3LayerRequestBody.getTableName(),
                h3LayerRequestBody.getQuery(),
                h3LayerRequestBody.getResolution()
        );
        return "test";
    }


    @GetMapping("/layers")
    String layers() throws InterruptedException {
        Thread.sleep(5000L);
        return "layers";
    }

    @GetMapping("/layers/{tableName}")
    String one(@PathVariable String tableName) {
        return h3Service.getLayerFromDatabase(tableName);
    }
}
