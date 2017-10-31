package com.example.nitesh.playo.model;


import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("created_at")
    private String createdAt;
    private String title;
    private String url;
    private String author;
    private String points;
    private int viewType;

    public News() {
    }

    public News(int viewType) {
        this.viewType = viewType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }

    public String getPoints() {
        return points;
    }

    public int getViewType() {
        return viewType;
    }
}
