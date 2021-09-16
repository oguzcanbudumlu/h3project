package com.h3project.server;

public class H3LayerRequestBody {
    private String tableName;
    private String query;
    private int resolution;

    public H3LayerRequestBody() {}

    public H3LayerRequestBody(String tableName, String query, int resolution) {
        this.tableName = tableName;
        this.query = query;
        this.resolution = resolution;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }



}
