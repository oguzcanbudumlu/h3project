package com.h3project.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class H3Dao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static String geojsonTemplate = "SELECT jsonb_build_object('type', 'FeatureCollection', 'features', jsonb_agg(feature) "
            + ") FROM (SELECT jsonb_build_object('type', 'Feature',"
            + "'geometry', ST_AsGeoJSON(geom)::jsonb"
            + ") AS feature FROM (%s) row) features";

    public String getGeojsonByQuery(String query) {
        String geojsonQuery = String.format(geojsonTemplate, query);
        return jdbcTemplate.queryForObject(geojsonQuery, String.class);
    }


}
