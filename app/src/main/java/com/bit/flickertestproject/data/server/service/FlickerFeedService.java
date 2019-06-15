package com.bit.flickertestproject.data.server.service;

import com.bit.flickertestproject.data.server.model.FlickerFeedResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickerFeedService {

    @GET(".")
    Single<FlickerFeedResponse> getFeed(@Query("format") String format, @Query("tags") String tags, @Query("ids") String ids, @Query("tagmode") String tagMode);

}
