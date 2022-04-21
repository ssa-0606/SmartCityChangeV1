package com.example.smartcitytestv1.city_acitivity.beans;

import java.util.List;

public class ActivityCategory {
    private int id;
    private String name;
    private int sort;
    private List<ActivityItem> activityItems;


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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<ActivityItem> getActivityItems() {
        return activityItems;
    }

    public void setActivityItems(List<ActivityItem> activityItems) {
        this.activityItems = activityItems;
    }

    public ActivityCategory(int id, String name, int sort, List<ActivityItem> activityItems) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.activityItems = activityItems;
    }
}
