package com.example.nitesh.playo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponseModel {

    @SerializedName("hits")
    private List<News> newsList;

    private String nbHits;
    private String page;
    private String nbPages;
    private String hitsPerPage;
    private String processingTimeMS;
    private boolean exhaustiveNbHits;
    private String query;
    private String params;


    public List<News> getNewsList() {
        return newsList;
    }

    public String getNbHits() {
        return nbHits;
    }

    public String getPage() {
        return page;
    }

    public String getNbPages() {
        return nbPages;
    }

    public String getHitsPerPage() {
        return hitsPerPage;
    }

    public String getProcessingTimeMS() {
        return processingTimeMS;
    }

    public boolean isExhaustiveNbHits() {
        return exhaustiveNbHits;
    }

    public String getQuery() {
        return query;
    }

    public String getParams() {
        return params;
    }
}
