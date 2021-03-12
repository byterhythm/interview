package com.shr.interview;

import android.util.Log;

import javax.inject.Inject;


public class DownloadManager {


    @Inject
    public DownloadManager() {

    }

    @Inject
    HttpClient httpClient;

    public void download(String url) {
        Log.i("dagger2", "DownloadManager download" + url);
        httpClient.init(url);
        //doSomething
    }

}
