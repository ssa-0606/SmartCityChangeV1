package com.example.smartcitytestv1.ui.home.beans;

public class LunBoImg {

    private int id ;
    private String advImg;
    private int targetId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdvImg() {
        return advImg;
    }

    public void setAdvImg(String advImg) {
        this.advImg = advImg;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public LunBoImg(int id, String advImg, int targetId) {
        this.id = id;
        this.advImg = advImg;
        this.targetId = targetId;
    }
}
