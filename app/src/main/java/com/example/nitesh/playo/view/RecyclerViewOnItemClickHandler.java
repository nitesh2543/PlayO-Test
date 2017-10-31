package com.example.nitesh.playo.view;

import android.view.View;

import com.example.nitesh.playo.utils.RecyclerViewEvents;


public class RecyclerViewOnItemClickHandler<T> {
    private final T item;
    private final RecyclerViewEvents.Listener listener;
    private final int position;

    public RecyclerViewOnItemClickHandler(T item, int position, RecyclerViewEvents.Listener listener) {
        this.item = item;
        this.position = position;
        this.listener = listener;
    }

    public void onClick(View view) {
        listener.onItemClick(item, view, position);

    }
}
