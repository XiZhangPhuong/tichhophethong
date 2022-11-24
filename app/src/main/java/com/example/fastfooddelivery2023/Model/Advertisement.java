package com.example.fastfooddelivery2023.Model;

public class Advertisement {
    private String id;
    private String linkImage;

    public Advertisement(String id, String linkImage) {
        this.id = id;
        this.linkImage = linkImage;
    }

    public Advertisement(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }
}
