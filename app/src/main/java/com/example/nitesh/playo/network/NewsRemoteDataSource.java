package com.example.nitesh.playo.network;

import com.example.nitesh.playo.model.NewsResponseModel;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

public class NewsRemoteDataSource  implements NewsDataSource {

    private NewsDataSource api;
    private String URL = "https://hn.algolia.com/";

    public NewsRemoteDataSource() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        this.api = retrofit.create(NewsDataSource.class);
    }

    @Override
    public Observable<NewsResponseModel> getQuestions(@Query("search") String query,@Query("page") int page) {
        return this.api.getQuestions(query,page);
    }
}
