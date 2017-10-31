package com.example.nitesh.playo.view;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nitesh.playo.R;
import com.example.nitesh.playo.model.News;
import com.example.nitesh.playo.model.NewsResponseModel;
import com.example.nitesh.playo.network.Network;
import com.example.nitesh.playo.network.NewsRemoteDataSource;
import com.example.nitesh.playo.presenter.MainPresenter;
import com.example.nitesh.playo.utils.Constants;
import com.example.nitesh.playo.utils.MainContract;
import com.example.nitesh.playo.utils.PaginationScrollListener;
import com.example.nitesh.playo.utils.RecyclerViewEvents;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeFragment extends Fragment implements RecyclerViewEvents.Listener<News>,
        MainContract.View, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private List<News> newsList;
    private MainPresenter presenter;
    private Constants.NewsItemClickListener listener;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private int page;
    private NewsResponseModel responseModel;
    private News footerView;
    private SearchView search;
    private TextView emptyView;
    private ProgressBar progressBar;
    private String query = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Constants.NewsItemClickListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsList = new ArrayList<>();
        presenter = new MainPresenter(new NewsRemoteDataSource(), Schedulers.io(),
                AndroidSchedulers.mainThread(), this, "", "sports", page);
        footerView = new News(RecyclerViewEvents.FOOTER);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.list);
        emptyView = view.findViewById(R.id.empty_view);
        progressBar = view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsList.clear();
        recyclerView.setAdapter(new NewsListAdapter(getActivity(), newsList, this));
        recyclerView.setOnScrollListener(new PaginationScrollListener(recyclerView.getLayoutManager()) {
            @Override
            protected void loadMoreItems() {
                showFooterView();
                presenter.loadData(query, ++page);
            }

            @Override
            public int getTotalPageCount() {
                return Integer.parseInt(responseModel.getNbPages());
            }

            @Override
            public boolean isLastPage() {
                return responseModel.isExhaustiveNbHits();
            }

            @Override
            public boolean isLoading() {
                return presenter.isLoading();
            }
        });
        if (!Network.isConnected(getActivity())) {
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            return;
        }
        presenter.subscribe();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        search.setOnQueryTextListener(this);
    }

    @Override
    public void onPause() {
        if (presenter != null)
            presenter.unsubscribe();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        if (presenter != null)
            presenter.onDestroy();
        super.onDestroy();

    }


    private void showFooterView() {
        if (newsList.add(footerView))
            recyclerView.getAdapter().notifyItemInserted(newsList.size() - 1);
    }

    private void hideFooterView() {
        if (newsList.remove(footerView))
            recyclerView.getAdapter().notifyItemRemoved(newsList.size());
    }

    @Override
    public void onItemClick(News item, View v, int position) {
        listener.onNewsItemClicked(item.getUrl());
    }

    @Override
    public void onFetchDataStarted() {
        Log.e(TAG, "onFetchDataStarted: ");
    }

    @Override
    public void onFetchDataCompleted() {
        Log.e(TAG, "onFetchDataCompleted: ");
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(newsList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFetchDataSuccess(NewsResponseModel newsResponseModel) {
        Log.e(TAG, "onFetchDataSuccess: ");
        responseModel = newsResponseModel;
        hideFooterView();
        newsList.addAll(newsResponseModel.getNewsList());
        recyclerView.getAdapter().notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        emptyView.setVisibility(newsList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onFetchDataError(Throwable e) {
        Log.e(TAG, "onFetchDataError: ");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!Network.isConnected(getActivity())) {
            Toast.makeText(getActivity(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            return false;
        }
        progressBar.setVisibility(View.VISIBLE);
        newsList.clear();
        this.query = query;
        presenter.loadData(this.query, page);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
