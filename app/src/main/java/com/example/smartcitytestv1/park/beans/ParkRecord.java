package com.example.smartcitytestv1.park.beans;

public class ParkRecord {

    private int id;
    private String plateNumber;
    private String monetary;
    private String parkName;
    private String entryTime;
    private String outTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getMonetary() {
        return monetary;
    }

    public void setMonetary(String monetary) {
        this.monetary = monetary;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public ParkRecord(int id, String plateNumber, String monetary, String parkName, String entryTime, String outTime) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.monetary = monetary;
        this.parkName = parkName;
        this.entryTime = entryTime;
        this.outTime = outTime;
    }
}
