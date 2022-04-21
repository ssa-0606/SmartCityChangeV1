package com.example.smartcitytestv1.city_acitivity.beans;

public class ActivityItem {
    private int id;
    private String name;
    private String content;
    private String imgUrl;
    private int signupNum;
    private int likeNum;
    private String status;
    private String publishTime;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getSignupNum() {
        return signupNum;
    }

    public void setSignupNum(int signupNum) {
        this.signupNum = signupNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public ActivityItem(int id, String name, String content, String imgUrl, int signupNum, int likeNum, String status, String publishTime) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.signupNum = signupNum;
        this.likeNum = likeNum;
        this.status = status;
        this.publishTime = publishTime;
    }
}
