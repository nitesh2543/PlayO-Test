package com.example.nitesh.playo.view;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nitesh.playo.R;
import com.example.nitesh.playo.utils.Constants;
import com.example.nitesh.playo.utils.FragmentHelper;


public class HomeActivity extends AppCompatActivity implements Constants.NewsItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentHelper.addFragment(this,R.id.home_container,new HomeFragment());
    }

    @Override
    public void onNewsItemClicked(String url) {
        Fragment fragment = new NewsDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.BundleKeys.URL,url);
        fragment.setArguments(bundle);
        FragmentHelper.replaceAndAddFragment(this,R.id.home_container,fragment);
    }
}
