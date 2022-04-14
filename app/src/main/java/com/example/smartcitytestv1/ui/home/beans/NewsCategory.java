package com.example.smartcitytestv1.ui.home.beans;

import java.util.List;

public class NewsCategory {
    private int id;
    private String name;
    private int sort;
    private List<NewsItem> newsItemList;

    public NewsCategory(int id, String name, int sort, List<NewsItem> newsItemList) {
        this.id = id;
        this.name = name;
        this.sort = sort;
        this.newsItemList = newsItemList;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<NewsItem> getNewsItemList() {
        return newsItemList;
    }

    public void setNewsItemList(List<NewsItem> newsItemList) {
        this.newsItemList = newsItemList;
    }
}
