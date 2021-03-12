package com.shr.interview;

import android.util.Log;

import javax.inject.Inject;


public class HttpClient {

    @Inject
    public HttpClient() {

    }

    public void init(String baseUrl) {
        Log.i("dagger2", "HttpClient init" + baseUrl);
    }


}
