package com.shr.interview;

import dagger.Component;

@Component
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
