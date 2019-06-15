package com.bit.flickertestproject.view.feed;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.bit.flickertestproject.R;
import com.bit.flickertestproject.data.server.model.Item;
import com.bit.flickertestproject.util.Constants;
import com.bit.flickertestproject.view.feed.adapter.FeedAdapter;
import com.bit.flickertestproject.view.feed.adapter.FeedAdapter.OnClickRecyclerViewItem;


public class FlickerFeedActivity extends AppCompatActivity implements OnClickRecyclerViewItem {

    private FlickerFeedViewModel flickerFeedViewModel;
    private FeedAdapter feedAdapter;
    private boolean isLoading = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flicker_feed);
        flickerFeedViewModel = new ViewModelProvider(this, new AndroidViewModelFactory(getApplication())).get(FlickerFeedViewModel.class);
        initView();
        addObserver();
        flickerFeedViewModel.getFlickerFeed();
    }


    private void initView() {
        progressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.rv_feed_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedAdapter = new FeedAdapter(this);
        recyclerView.setAdapter(feedAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();


                if (! isLoading) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == feedAdapter.getItemCount() - 1) {
                        showProgressView();
                        flickerFeedViewModel.getFlickerFeed();
                        isLoading = true;
                    }
                }
            }
        });

    }


    private void addObserver() {
        flickerFeedViewModel.getFeedResponseMutableLiveData().observe(this, flickerFeedResponse -> {
            hideProgressView();
            feedAdapter.updateFeedView(flickerFeedResponse.getItems());
            isLoading = false;

        });
        flickerFeedViewModel.getFeedErrorMutableLiveData().observe(this, throwable -> {
            hideProgressView();
            isLoading = false;
        });
    }

    private void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressView() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void onClick(final Item item) {
        try {
            FullScreenIVFragment fullScreenDialogFragment = new FullScreenIVFragment();
            fullScreenDialogFragment.setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDialog);
            Bundle data = new Bundle();
            data.putString(Constants.IMAGE_PATH_KEY, item.getMedia().getcImage());
            fullScreenDialogFragment.setArguments(data);
            fullScreenDialogFragment.show(getSupportFragmentManager(), "FullScreenImageView");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


