package com.example.nitesh.playo.network;

import com.example.nitesh.playo.model.NewsResponseModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;


public interface NewsDataSource {

    @GET("api/v1/search")
    Observable<NewsResponseModel> getQuestions(@Query("query") String searchQuery , @Query("page") int page);

}
