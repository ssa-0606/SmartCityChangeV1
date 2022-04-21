package com.example.smartcitytestv1.city_acitivity.beans;

public class CommentUser {
    private String nickName;
    private String content;
    private String commentTime;
    private String avatar;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public CommentUser(String nickName, String content, String commentTime, String avatar) {
        this.nickName = nickName;
        this.content = content;
        this.commentTime = commentTime;
        this.avatar = avatar;
    }
}
