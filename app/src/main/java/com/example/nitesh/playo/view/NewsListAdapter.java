package com.example.nitesh.playo.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.nitesh.playo.BR;
import com.example.nitesh.playo.R;
import com.example.nitesh.playo.databinding.ListItemBinding;
import com.example.nitesh.playo.model.News;
import com.example.nitesh.playo.utils.RecyclerViewEvents;

import java.util.List;



public class NewsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final LayoutInflater layoutInflater;
    private final List<News> list;
    private final RecyclerViewEvents.Listener<News> listener;
    private ListItemBinding binding;

    public NewsListAdapter(Context context, List<News> list, RecyclerViewEvents.Listener<News> listener) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case RecyclerViewEvents.FOOTER:
                return new FooterViewHolder(layoutInflater.inflate(R.layout.footer_progress_bar, parent, false));
            default:
                binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false);
                return new RecyclerViewHolder<>(binding);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewHolder) {
            News item = list.get(position);
            RecyclerViewHolder newsViewHolder = (RecyclerViewHolder) holder;
            newsViewHolder.getBinding().setVariable(BR.clickListener, new RecyclerViewOnItemClickHandler<>(item, position, listener));
            newsViewHolder.getBinding().setVariable(BR.news, item);
            newsViewHolder.getBinding().executePendingBindings();
        }
    }

    @Override
    public int getItemViewType(int position) {
        News item = list.get(position);
        return item.getViewType();
    }

    public int getItemCount() {
        return list.size();
    }

}
