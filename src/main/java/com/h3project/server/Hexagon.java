package com.h3project.server;

public class Hexagon {
    private String hexId;
    private double pointCountRatio;

    public Hexagon(String hexId, double pointCountRatio) {
        this.hexId = hexId;
        this.pointCountRatio = pointCountRatio;
    }

    public String getHexId() {
        return hexId;
    }

    public double getPointCountRatio() {
        return pointCountRatio;
    }
}
