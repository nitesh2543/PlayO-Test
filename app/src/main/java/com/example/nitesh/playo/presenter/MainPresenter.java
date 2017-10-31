package com.example.nitesh.playo.presenter;

import android.support.annotation.NonNull;

import com.example.nitesh.playo.model.NewsResponseModel;
import com.example.nitesh.playo.network.NewsDataSource;
import com.example.nitesh.playo.utils.MainContract;

import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class MainPresenter implements MainContract.Presenter {

    @NonNull
    private NewsDataSource newsDataSource;

    @NonNull
    private Scheduler backgroundScheduler;

    @NonNull
    private Scheduler mainScheduler;

    @NonNull
    private CompositeSubscription subscriptions;

    private MainContract.View view;

    @NonNull
    private String url;

    private String query;

    private int page;

    private boolean isLoading;


    public MainPresenter(
            @NonNull NewsDataSource newsDataSource,
            @NonNull Scheduler backgroundScheduler,
            @NonNull Scheduler mainScheduler,
            MainContract.View view,
            @NonNull String url, String query, int page) {
        this.newsDataSource = newsDataSource;
        this.backgroundScheduler = backgroundScheduler;
        this.mainScheduler = mainScheduler;
        this.view = view;
        subscriptions = new CompositeSubscription();
        this.url = url;
        this.query = query;
        this.page = page;
    }


    @Override
    public void loadData(String query, int page) {
        final String q = query.replaceAll(" ","");
        this.isLoading = true;
        view.onFetchDataStarted();
        subscriptions.clear();
        Subscription subscription = newsDataSource
                .getQuestions(q, page)
                .subscribeOn(backgroundScheduler)
                .observeOn(mainScheduler)
                .subscribe(new Observer<NewsResponseModel>() {
                    @Override
                    public void onCompleted() {
                        view.onFetchDataCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onFetchDataError(e);
                    }

                    @Override
                    public void onNext(NewsResponseModel rootModel) {
                        view.onFetchDataSuccess(rootModel);
                        isLoading = false;
                    }
                });

        subscriptions.add(subscription);
    }

    @Override
    public void subscribe() {
        loadData(query, page);
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }

    @Override
    public void onDestroy() {
        this.view = null;
    }

    public boolean isLoading() {
        return isLoading;
    }
}
