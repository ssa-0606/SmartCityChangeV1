package com.example.smartcitytestv1.metro.beans;

import java.util.List;

public class MetroStationLine {

    private int id;
    private String name;
    private String first ;
    private String end;
    private int stationsNumber;
    private int km;
    private String runStationsName;
    private int remainingTime;
    private List<Line> lines;

    public MetroStationLine(int id, String name, String first, String end, int stationsNumber, int km, String runStationsName, int remainingTime, List<Line> lines) {
        this.id = id;
        this.name = name;
        this.first = first;
        this.end = end;
        this.stationsNumber = stationsNumber;
        this.km = km;
        this.runStationsName = runStationsName;
        this.remainingTime = remainingTime;
        this.lines = lines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getStationsNumber() {
        return stationsNumber;
    }

    public void setStationsNumber(int stationsNumber) {
        this.stationsNumber = stationsNumber;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public String getRunStationsName() {
        return runStationsName;
    }

    public void setRunStationsName(String runStationsName) {
        this.runStationsName = runStationsName;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
}
