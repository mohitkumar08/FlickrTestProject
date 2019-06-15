package com.bit.flickertestproject.data.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Interceptor.Chain;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import okio.Okio;

public class FlickerResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.body() == null) {
            return response;
        }

        JSONObject jsonObject = null;
        String str = response.body().string();
        try {
            jsonObject = new JSONObject(str.substring(str.indexOf("{"), str.lastIndexOf("}") + 1));
            MediaType contentType = response.body().contentType();
            ResponseBody body = ResponseBody.create(contentType, jsonObject.toString());
            return response.newBuilder().body(body).build();
        } catch (JSONException e) {
            e.printStackTrace();
            return response;
        }

    }
}

