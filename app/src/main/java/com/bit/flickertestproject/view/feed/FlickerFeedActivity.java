package com.bit.flickertestproject.view.feed;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bit.flickertestproject.R;

public class FlickerFeedActivity extends AppCompatActivity {

    FlickerFeedViewModel flickerFeedViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker_feed);
        flickerFeedViewModel = new ViewModelProvider(this, new AndroidViewModelFactory(getApplication())).get(FlickerFeedViewModel.class);

    }
}
