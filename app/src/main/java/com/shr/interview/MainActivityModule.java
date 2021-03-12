package com.shr.interview;

import dagger.Module;
import dagger.Provides;


@Module
public class MainActivityModule {
    String url;

    MainActivityModule(String url) {
        this.url = url;
    }

    @Provides
    public DownloadManager providerManager(){
        return new DownloadManager();
    }
}
