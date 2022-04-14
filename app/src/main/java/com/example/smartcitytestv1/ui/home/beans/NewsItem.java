package com.example.smartcitytestv1.ui.home.beans;

public class NewsItem {
    private int id;
    private String cover;
    private String title;
    private String content;
    private String publishDate;
    private int commentNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public NewsItem(int id, String cover, String title, String content, String publishDate, int commentNum) {
        this.id = id;
        this.cover = cover;
        this.title = title;
        this.content = content;
        this.publishDate = publishDate;
        this.commentNum = commentNum;
    }
}
