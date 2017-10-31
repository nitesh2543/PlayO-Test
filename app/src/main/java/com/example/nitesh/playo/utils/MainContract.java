package com.example.nitesh.playo.utils;


import com.example.nitesh.playo.model.NewsResponseModel;

public interface MainContract {


        interface View {

            void onFetchDataStarted();

            void onFetchDataCompleted();

            void onFetchDataSuccess(NewsResponseModel newsResponseModel);

            void onFetchDataError(Throwable e);
        }

        interface Presenter {

            void loadData(String query,int page);

            void subscribe();

            void unsubscribe();

            void onDestroy();

        }
}
