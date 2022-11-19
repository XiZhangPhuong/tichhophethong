package com.example.fastfooddelivery2023.Model;

public class VideoShort {
    private String id;
    private String video;
    private String title;
    private String information;
    private int like;

    public VideoShort() {
    }

    public VideoShort(String id, String video, String title, String information, int like) {
        this.id = id;
        this.video = video;
        this.title = title;
        this.information = information;
        this.like = like;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
