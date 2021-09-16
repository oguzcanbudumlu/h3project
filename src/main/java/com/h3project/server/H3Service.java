package com.h3project.server;

import com.LogColors;
import com.uber.h3core.H3Core;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class H3Service {

    @Autowired
    Logger LOG;

    @Autowired
    H3Dao h3Dao;

    H3Core h3Core = H3Core.newInstance();

    public H3Service() throws IOException {
    }



    @Async
    public CompletableFuture<Object> calculateLayer(int resolution, String query) throws InterruptedException {
        LOG.info("getLayer start");
        LOG.info(LogColors.ANSI_RED + Thread.currentThread().getName() + LogColors.ANSI_RESET);
        String geojson = h3Dao.getGeojson(query);
        Thread.sleep(3000L);
//        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture(geojson);
//        String s = completedFuture.get();
        String layer = calculateLayer(geojson, resolution);
        LOG.info("getLayer end");
        return CompletableFuture.completedFuture(layer);
    }

    @Async
    public CompletableFuture<Object> testAnotherThread() throws InterruptedException {
        LOG.info(LogColors.ANSI_RED + Thread.currentThread().getName() + LogColors.ANSI_RESET);
        Thread.sleep(4000L);
        return CompletableFuture.completedFuture("testAnotherThread");
    }

    String calculateLayer(String geojson, int resolution) {
        LOG.info(geojson);
        LOG.info(String.valueOf(resolution));


        JSONObject geojsonObject = new JSONObject(geojson);
        JSONObject layer = calculateLayer(geojsonObject, resolution);
        JSONObject normalizedLayer = normalizeLayer(layer);

        return normalizedLayer.toString();
    }



    JSONObject calculateLayer(JSONObject geojson, int resolution) {
        JSONObject layer = new JSONObject();
        JSONArray features = geojson.getJSONArray("features");

        for(int i = 0; i < features.length(); i++) {
            JSONObject feature = features.getJSONObject(i);
            JSONObject geometry = feature.getJSONObject("geometry");
            JSONArray coordinates = geometry.getJSONArray("coordinates");
            double lng = coordinates.getDouble(0);
            double lat = coordinates.getDouble(1);

            String h3Index = Long.toHexString(h3Core.geoToH3(lat, lng, resolution));

            int point;

            if(layer.has(h3Index)) {
                point = (int) layer.get(h3Index) + 1;
            } else {
                point = 1;
            }

            layer.put(h3Index, point);
        }

        return layer;
    }

    JSONObject normalizeLayer(JSONObject layer) {
        MinMax minMax = getMinMaxCountsPair(layer);
        return normalizeLayer(layer, minMax.getMin(), minMax.getMax());
    }

    JSONObject normalizeLayer(JSONObject layer, int min, int max) {
        for(Iterator<String> it = layer.keys(); it.hasNext();) {
            String hexagonId = it.next();
            try {
                double normalizedValue = (layer.getInt(hexagonId) - min) / ((max - min) * 1.0);
                layer.put(hexagonId, normalizedValue);
            } catch (ArithmeticException e) {
                layer.put(hexagonId, 1);
            }
        }

        return layer;
    }


    MinMax getMinMaxCountsPair(JSONObject layer) {
        Iterator<String> hexagonIds = layer.keys();
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for(Iterator<String> it = hexagonIds; it.hasNext(); ) {
            String hexagonId = it.next();

            int count = layer.getInt(hexagonId);

            if(count > max) {
                max = count;
            }

            if(count < min) {
                min = count;
            }
        }

        return new MinMax(min, max);
    }

    List<Hexagon> getHexagonList(JSONObject layer) {
        List<Hexagon> hexagons = new ArrayList<>();

        for(Iterator<String> it = layer.keys(); it.hasNext(); ) {
            String hexId = it.next();
            hexagons.add(new Hexagon(hexId, layer.getDouble(hexId)));
        }

        return hexagons;
    }


    void createLayer(String tableName, String query, int resolution) {
        String geojson = h3Dao.getGeojson(query);
        JSONObject layer = new JSONObject(calculateLayer(geojson, resolution));
        h3Dao.saveLayer(tableName, getHexagonList(layer));
    }



    public String getLayerFromDatabase(String tableName) {
        return h3Dao.getLayer(tableName);
    }
}
