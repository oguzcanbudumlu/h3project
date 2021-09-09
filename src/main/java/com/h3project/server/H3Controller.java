package com.h3project.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class H3Controller {
    @Autowired
    H3Service h3Service;

    @RequestMapping(value = "/quakestr-dataset-as-geojson", method = RequestMethod.GET)
    public String getGeojsonByQueryTest() {
        return this.h3Service.getGeojsonByQuery("select * from quakes_tr");
    }
}
