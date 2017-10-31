package com.example.nitesh.playo.utils;


import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;

public class BindingUtils {
    private static final String TAG = BindingUtils.class.getSimpleName();

    @BindingAdapter(value = {"android:src", "default"}, requireAll = true)
    public static void bindImage(ImageView view, String url, Drawable placeHolder) {
        if (TextUtils.isEmpty(url))
            url = null;
        RequestCreator requestCreator =
                Picasso.with(view.getContext()).load(url);

        if (placeHolder != null) {
            requestCreator.placeholder(placeHolder);
        }
        requestCreator.into(view);
    }

    @BindingAdapter({"android:src"})
    public static void loadImage(ImageView view, String url) {

        Picasso.with(view.getContext()).load(url).into(view);
    }


    @BindingAdapter({"date"})
    public static void setDate(TextView view, String date) {
        try {
            PrettyTime time = new PrettyTime();
            view.setText(time.format(DateUtils.parse(date, DateUtils.NEWS_LISTING)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @BindingAdapter({"htmlText"})
    public static void setTitle(TextView view, String title) {
        view.setText(Html.fromHtml(title));
    }


    @BindingAdapter({"app:author"})
    public static void loadAuthorName(TextView view, String description) {
        String data = description.substring(0, 1).toUpperCase() + description.substring(1, description.length());
        view.setText(data);
    }

    @BindingAdapter({"app:category"})
    public static void setCategory(TextView view, String category) {
        if (!TextUtils.isEmpty(category)) {
            view.setText(category);
            view.setVisibility(View.VISIBLE);
        } else
            view.setVisibility(View.INVISIBLE);
    }
}
