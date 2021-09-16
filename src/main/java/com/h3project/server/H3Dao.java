package com.h3project.server;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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



    public String getGeojson(String query) {
        String geojsonQuery = String.format(geojsonTemplate, query);
        return jdbcTemplate.queryForObject(geojsonQuery, String.class);
    }

    public boolean saveLayer(String tableName, List<Hexagon> layer) {
        createEmptyTable(tableName);
        insertEachHexagon(tableName, layer);
        return true;
    }


    private void createEmptyTable(String tableName) {
        jdbcTemplate.execute("create table " + tableName +
                " (id serial primary key, " +
                "hex_id varchar(50) unique not null, " +
                "point_count_ratio double precision not null)");
    }

    public void insertEachHexagon(String tableName, List<Hexagon> layer) {
        String insertSql = String.format("insert into %s (hex_id, point_count_ratio) values (?, ?)", tableName);

        jdbcTemplate.batchUpdate(insertSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                Hexagon hexagon = layer.get(i);
                preparedStatement.setString(1, hexagon.getHexId());
                preparedStatement.setDouble(2, hexagon.getPointCountRatio());
            }

            @Override
            public int getBatchSize() {
                return layer.size();
            }
        });
    }

    public String getLayer(String tableName) {
        String query = "select * from " + tableName;
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);
        return convertMapListToJson(result).toString();
    }

    private JSONObject convertMapListToJson(List<Map<String, Object>> mapList) {
        JSONObject jsonObject = new JSONObject();
        for(Map<String, Object> map: mapList) {
            String hexId = (String) map.get("hex_id");
            double pointCountRatio = (double) map.get("point_count_ratio");
            jsonObject.put(hexId, pointCountRatio);
        }

        return jsonObject;
    }




}
