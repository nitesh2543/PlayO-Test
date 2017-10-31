package com.example.nitesh.playo;


import com.example.nitesh.playo.model.NewsResponseModel;
import com.example.nitesh.playo.network.NewsDataSource;
import com.example.nitesh.playo.presenter.MainPresenter;
import com.example.nitesh.playo.utils.MainContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MainPresenterTest implements MainContract {

    @Mock
    private NewsDataSource newsDataSource;

    @Mock
    private MainContract.View view;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchValidDataShouldLoadIntoView() {
        NewsResponseModel newsResponseModel = new NewsResponseModel();
        when(newsDataSource.getQuestions("", 0)).thenReturn(Observable.just(newsResponseModel));
        MainPresenter mainPresenter = new MainPresenter(
                this.newsDataSource,
                Schedulers.immediate(),
                Schedulers.immediate(),
                this.view,
                "", "", 0
        );

        mainPresenter.loadData("", 0);

    }

    @Test
    public void fetchErrorShouldReturnErrorToView() {

        Exception exception = new Exception();

        when(newsDataSource.getQuestions("", 0))
                .thenReturn(Observable.<NewsResponseModel>error(exception));

        MainPresenter mainPresenter = new MainPresenter(
                this.newsDataSource,
                Schedulers.immediate(),
                Schedulers.immediate(),
                this.view,
                "", "", 0
        );

        mainPresenter.loadData("", 0);

        InOrder inOrder = Mockito.inOrder(view);
        inOrder.verify(view, times(1)).onFetchDataStarted();
        inOrder.verify(view, times(1)).onFetchDataError(exception);
        verify(view, never()).onFetchDataCompleted();
    }
}
