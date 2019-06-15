package com.bit.flickertestproject.view.feed;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;

import com.bit.flickertestproject.BuildConfig;
import com.bit.flickertestproject.R;
import com.bit.flickertestproject.data.server.NetworkClient;
import com.bit.flickertestproject.data.server.model.FlickerFeedResponse;
import com.bit.flickertestproject.data.server.model.Item;
import com.bit.flickertestproject.data.server.service.FlickerFeedService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FlickerFeedViewModel extends AndroidViewModel {

    private MutableLiveData<FlickerFeedResponse> feedResponseMutableLiveData = new MutableLiveData();
    private MutableLiveData<Throwable> feedErrorMutableLiveData = new MutableLiveData();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FlickerFeedService flickerFeedService;

    private StringBuilder ids = new StringBuilder();
    private final static String FEED_TYPE = "json";
    private final static String TAG_MODE = "any";

    private List<String> tags;

    public FlickerFeedViewModel(@NonNull final Application application) {
        super(application);
        flickerFeedService = new NetworkClient().getRetrofitClientInstance(BuildConfig.HOST_URL).create(FlickerFeedService.class);
        tags = Arrays.asList(application.getResources().getStringArray(R.array.flicker_popular_tags));
    }


    void getFlickerFeed() {
        flickerFeedService.getFeed(FEED_TYPE, getTags(), ids.toString(),TAG_MODE).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<FlickerFeedResponse>() {
            @Override
            public void onSubscribe(final Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(final FlickerFeedResponse flickerFeedResponse) {
                feedResponseMutableLiveData.postValue(flickerFeedResponse);
                ids = new StringBuilder();
                Observable.just(flickerFeedResponse.getItems()).subscribeOn(Schedulers.computation()).flatMap((Function<List<Item>, ObservableSource<Item>>) items -> Observable.fromIterable(items)).subscribe(new Observer<Item>() {
                    @Override
                    public void onSubscribe(final Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(final Item item) {
                        if (item.getAuthorId() != null && ! item.getAuthorId().isEmpty()) {
                            ids.append(item.getAuthorId()).append(",");
                        }
                    }

                    @Override
                    public void onError(final Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


            }

            @Override
            public void onError(final Throwable e) {
                feedErrorMutableLiveData.postValue(e);

            }
        });

    }

    public MutableLiveData<FlickerFeedResponse> getFeedResponseMutableLiveData() {
        return feedResponseMutableLiveData;
    }

    public MutableLiveData<Throwable> getFeedErrorMutableLiveData() {
        return feedErrorMutableLiveData;
    }

    private String getTags() {
        Collections.shuffle(tags);
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            temp.append(tags.get(i)).append(",");
        }
        return temp.toString();


    }

}
