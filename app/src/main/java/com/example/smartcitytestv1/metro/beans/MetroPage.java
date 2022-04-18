package com.example.smartcitytestv1.metro.beans;

public class MetroPage {
    private int lineId;
    private String lineName;

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public MetroPage(int lineId, String lineName) {
        this.lineId = lineId;
        this.lineName = lineName;
    }
}
